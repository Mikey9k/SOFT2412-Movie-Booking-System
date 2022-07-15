package movie_booking;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.FileReader;
import java.time.LocalDate;

import java.util.*;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.time.format.*;
import movie_booking.Databases.*;

/**
 * LogicHandler Constructor
 *
 * @param in           Inputstream injected for user input or testing input
 * @param out          Outputstream injected for output testing
 * @param resourcePath filepath for resources folder - for testing purposes
 */

public class LogicHandler {
    static int timeoutSeconds = 60 * 2;

    private VirtConsole con;
    private String resourcePath;

    private LoginDatabase loginDB;
    private GiftCardDatabase giftcardDB;
    private ConfigDatabase config;
    private CreditCardDatabase ccDB;
    private MovieDatabase mvDB;
    private CancellationDatabase cDB;

    private boolean loggedIn = false;
    private User loggedInUser;

    public LogicHandler(VirtConsole con, String resourcePath) {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(resourcePath + "/config.json"));
            config = gson.fromJson(reader, ConfigDatabase.class);
            config.resourcePath = resourcePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        timeoutSeconds = config.timeoutSeconds;

        this.con = con;
        this.resourcePath = resourcePath;

        // Load using resourcePath
        loginDB = new LoginDatabase(this.resourcePath, con);
        giftcardDB = new GiftCardDatabase(this.resourcePath, con);
        ccDB = new CreditCardDatabase(this.resourcePath);
        mvDB = new MovieDatabase(resourcePath);
        cDB = new CancellationDatabase(resourcePath);
    }

    /**
     * Displays user options (autologin as guest)
     */
    public void run() {
        boolean running = true;
        promptLoop: while (running) {
            con.println("");
            con.println("Welcome to HOYTSOFT movie bookings.");
            con.println("------------------------------");
            con.println("Enter '1' to log in");
            con.println("Enter '2' to register");
            con.println("Enter '3' to browse as a guest");
            con.println("Enter 'q' or 'cancel' to exit");
            con.println("------------------------------");
            String selection = con.nextLine();

            loggedInUser = null; // reset the user login
            loggedIn = false;

            switch (selection) {

                case "1":
                    this.login();
                    break;

                case "2":
                    // Register as a customer
                    User newUser = this.loginDB.registerUserInterface("customer");
                    if (newUser != null) {
                        loggedInUser = newUser;
                        loggedIn = true;
                        while (!this.browse())
                            ;
                    }
                    break;

                case "3":
                    // Guest browse
                    while (!this.browse())
                        ;
                    break;

                case "q":
                case "cancel":
                    running = false;
                    break;

                default:
                    con.println("Invalid option. Please try again.");
                    continue promptLoop;
            }
        }
        con.println("Thank you for using HOYTSOFT booking systems.");
        con.println("Please come again!");
    }

    /**
     * Allows user to login as a customer
     * 
     * @return true to exit with successful login, false if unsuccessful
     */
    public boolean login() {
        return this.login(false);
    }

    /**
     * Allows user to login as a customer
     * 
     * @param nonInteractive: Whether to return immediately on login success.
     * @return true to exit with successful login, false if unsuccessful
     */
    public boolean login(boolean nonInteractive) {

        // Get username
        con.println("Please enter your username.\n" + "Or enter 'cancel' to cancel.");
        String userName = con.nextLine();

        if (userName.equals("cancel")) {
            con.println("Login cancelled.\n");
            return false;
        }

        if (userName.equals("")) {
            con.println("\nUsername blank, login failed!");
            return false;
        }

        // Get password
        con.println("\nPlease enter your password.\n" + "Or enter 'cancel' to cancel.");
        String password = con.readPassword();

        if (password.equals("cancel")) {
            con.println("Login cancelled.\n");
            return false;
        }

        if (password.equals("")) {
            con.println("Password blank, login failed!");
            return false;
        }

        if (this.loginDB.verifyLogin(userName, password)) {
            con.println("\nLogin successful!");
            // Give user options of what to do -> guest options + book movie

            this.loggedIn = true;
            loggedInUser = loginDB.getUser(userName);
            if (nonInteractive)
                return true;

            switch (loggedInUser.getUserType()) {
                case "manager":
                    StaffScreen staffScreen = new StaffScreen(con, loginDB, mvDB, giftcardDB);
                    ManagerScreen s = new ManagerScreen(con, loginDB, staffScreen, cDB);
                    while (s.run())
                        ;
                    return true;

                case "staff":
                    StaffScreen staffScreen1 = new StaffScreen(con, loginDB, mvDB, giftcardDB);
                    while (staffScreen1.adminMenu())
                        ;
                    return true;

                default:
                    while (!this.browse())
                        ;
                    return true;
            }
        } else {
            con.println("\nLogin failed!");
            return false;
        }
    }

    /**
     * Allows user to browse through movies and apply filters
     * 
     * @return true to exit
     */
    public boolean browse() {
        mvDB.reset();

        boolean browsing = true;
        promptLoop: while (browsing) {

            con.println("");
            mvDB.displayPage(con);
            mvDB.displayFilters(con);
            con.println("------------------------------");
            con.println("Enter 'M' to select a (M)ovie for more details");
            con.println("Enter 'L' to filter by Cinema (L)ocation");
            con.println("Enter 'S' to filter by Cinema (S)creen Size");
            con.println("Enter 'A' to clear (A)ll filters.");
            con.println("Enter a number to jump to that page.");
            con.println("------------------------------");
            con.println("Enter 'cancel' to cancel booking");
            con.println("------------------------------");
            String selection = con.nextLine();

            try {
                int page = Integer.parseInt(selection);
                if (page <= 0 || page > mvDB.getPageCount()) {
                    con.println("Invalid selection.");
                    continue;
                }
                mvDB.setPage(page);
                continue promptLoop;

            } catch (NumberFormatException e) {
            }

            switch (selection) {

                case "M":
                    // Select movie by movie option number
                    int optionNumber;

                    con.println("Please enter a movie option number:");
                    con.println("Or enter 'cancel' to go back.");
                    selection = con.nextLine();

                    if (selection.equals("cancel")) {
                        con.println("Taking you back to the movie menu...\n");
                        break;
                    }

                    try {
                        optionNumber = Integer.parseInt(selection);
                    } catch (NumberFormatException e) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    if (mvDB.selectMovie(optionNumber) == false) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    return this.browseDetails();

                case "L":
                    // Filter by cinema location
                    con.println("Please enter a cinema location from this list:");
                    for (var c : mvDB.getCinemas()) {
                        con.format("\t%s", c);
                    }
                    con.println("\nOr enter 'cancel' to go back.");

                    selection = con.nextLine();

                    if (selection.equals("cancel")) {
                        con.println("Taking you back to the movie menu...\n");
                        break;
                    }

                    // Implement location filter
                    if (mvDB.cinemaExists(selection))
                        mvDB.setCinemaFilter(selection);
                    else {
                        con.println("Invalid cinema. The operation has been aborted.");
                    }
                    break;

                case "S":
                    // Filter by cinema screen size
                    con.println("Please select a screen size from this list:");
                    for (String s : mvDB.displayScreenSizes()) {
                        con.format("\t%s", s);
                    }
                    con.println("\nOr enter 'cancel' to go back.");

                    selection = con.nextLine();

                    if (selection.equals("cancel")) {
                        con.println("Taking you back to the movie menu...\n");
                        break;
                    }

                    // Implement screen size filter
                    if (mvDB.displayScreenSizes().contains(selection))
                        mvDB.setScreenSizeFilter(selection);
                    else {
                        con.println("Invalid screen size. The operation has been aborted.");
                    }

                    break;

                case "A":
                    mvDB.reset();
                    break;

                case "cancel":
                    con.println("Booking has been cancelled.\n");
                    return true;

                default:
                    con.println("Invalid option. Please try again.");
                    continue promptLoop;
            }
        }
        return false;
    }

    /**
     * Displays the movie option details
     * 
     * @return true to exit
     */
    public boolean browseDetails() {
        mvDB.displayMovieDetails(con);

        while (true) {
            // Allow user to select movie or go back
            con.println("Would you like to book this movie? (Y/N)");
            String selection = con.nextLine();

            switch (selection) {

                case "Y":
                    return this.makeBooking();

                case "N":
                    return false;

                default:
                    con.println("Invalid option. Please try again.");
                    continue;
            }
        }
    }

    /**
     * Makes booking
     * 
     * @return true to logout
     */
    public boolean makeBooking() {
        // Proceed to booking tickets
        MovieScreening selectedScreening = null;

        promptLoop: while (true) {
            con.println("Which day would you like to book for?");
            con.println("Or enter 'cancel' to abort the booking.");
            String dayOfWeek = con.nextLine();

            if (dayOfWeek.equalsIgnoreCase("cancel")) {
                con.println("Booking has been cancelled.\n");
                return false;
            }

            if (!Arrays.stream(Movie.days).skip(LocalDate.now().getDayOfWeek().getValue() - 1)
                    .anyMatch(dayOfWeek::equals)) {
                con.println("Invalid day!");
                continue promptLoop;
            }

            con.println("Please enter the desired screening time (HH:MM): ");
            con.println("Or enter 'cancel' to abort the booking.");
            String screeningTime = con.nextLine();

            if (dayOfWeek.equalsIgnoreCase("cancel")) {
                con.println("Booking has been cancelled.\n");
                return false;
            }

            LocalTime time;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                time = LocalTime.parse(screeningTime, formatter);
            } catch (DateTimeParseException e) {
                con.println("Invalid screening time.");
                continue promptLoop;
            }

            if (!mvDB.getSelectedMovie().checkShowAvailable(dayOfWeek, time)) {
                con.println("Invalid screening time.");
                continue promptLoop;
            }

            selectedScreening = mvDB.getSelectedMovie().getMovieScreening(dayOfWeek, time);

            if (selectedScreening.getAvailableSeats() <= 0) {
                con.println("This screening is sold out.");

                while (true) {
                    con.println("Would you like to choose another screening? (Y/N)");
                    String selection = con.nextLine();

                    switch (selection) {

                        case "Y":
                            continue promptLoop;

                        case "N":
                            con.println("You have been returned to the movie selection menu.");
                            return false;

                        default:
                            con.println("Invalid option. Please try again.");
                            continue;
                    }
                }
            }

            break;
        }
        try {
            Booking booking = this.addTickets(selectedScreening, mvDB.getSelectedMovie().getScreenSize());
            if (booking != null) {
                booking.finalise();
                mvDB.save();
            }
        } catch (TimeoutException e) {
            this.cDB.addCancellation(new Cancellation("Timeout", LocalDateTime.now(), loggedInUser));
            con.println("\n\nYour session has expired. You have been logged out.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds the number of tickets and the types of tickets
     *
     * @return Booking class that contains all the ticket information
     */
    public Booking addTickets(MovieScreening selectedScreening, String screenSize) throws TimeoutException {

        int numChildTickets = 0;
        int numStudentTickets = 0;
        int numAdultTickets = 0;
        int numPensionerTickets = 0;

        Map<String, Double> ticketPrice = this.config.ticketPrices.get(screenSize);

        con.println("Thank you for selecting the movie you want to book tickets for!\n");
        con.format("There are %d total seats available for this screening.\n", selectedScreening.getAvailableSeats());
        con.format("\tFront section: %d\tMiddle section: %d\tBack section: %d\n",
                selectedScreening.getSeatsAvailableFront(), selectedScreening.getSeatsAvailableMiddle(),
                selectedScreening.getSeatsAvailableBack());

        promptLoop: while (true) {
            int totalNumTickets = numChildTickets + numStudentTickets + numAdultTickets + numPensionerTickets;
            con.println("\n------------------------------");
            con.println("You have currently booked");
            con.format("\t%d X Child ($%.2f)\t%d X Student ($%.2f)\t%d X Adult ($%.2f)\t%d X Pensioner ($%.2f)\n",
                    numChildTickets, ticketPrice.get("child"), numStudentTickets, ticketPrice.get("student"),
                    numAdultTickets, ticketPrice.get("adult"), numPensionerTickets, ticketPrice.get("pensioner"));
            con.println("tickets.");
            double total = 0;
            total += ticketPrice.get("child") * numChildTickets;
            total += ticketPrice.get("student") * numStudentTickets;
            total += ticketPrice.get("adult") * numAdultTickets;
            total += ticketPrice.get("pensioner") * numPensionerTickets;
            con.format("Total: $%.2f\n", total);
            con.println("------------------------------");
            con.println("Please select the type of ticket you would like to book.\n"
                    + "Enter 'C' for child (under 12 years) tickets.\n"
                    + "Enter 'S' for student tickets (must have valid student id if requested).\n"
                    + "Enter 'A' for adult tickets.\n" + "Enter 'P' for senior (pensioner) tickets.\n\n"
                    + "Enter 'F' when you are finished adding the tickets you would like.");
            con.println("Enter 'cancel' to cancel booking.\n");
            String selection = con.nextLine();
            int increment;
            switch (selection) {

                case "C":
                    // adding child tickets
                    increment = this.addNumberTickets("child", selectedScreening.getAvailableSeats() - totalNumTickets);

                    // Cancel
                    if (increment == -1) {
                        return null;
                    }

                    numChildTickets += increment;
                    break;

                case "S":
                    // add student tickets
                    increment = this.addNumberTickets("student",
                            selectedScreening.getAvailableSeats() - totalNumTickets);

                    // Cancel
                    if (increment == -1) {
                        return null;
                    }

                    numStudentTickets += increment;
                    break;

                case "A":
                    // add adult tickets
                    increment = this.addNumberTickets("adult", selectedScreening.getAvailableSeats() - totalNumTickets);

                    // Cancel
                    if (increment == -1) {
                        return null;
                    }

                    numAdultTickets += increment;
                    break;

                case "P":
                    // add senior/pensioner tickets
                    increment = this.addNumberTickets("senior/pensioner",
                            selectedScreening.getAvailableSeats() - totalNumTickets);

                    // Cancel
                    if (increment == -1) {
                        return null;
                    }

                    numPensionerTickets += increment;
                    break;

                case "F":
                    // finished adding tickets
                    if (totalNumTickets == 0) {
                        con.println("You currently have no tickets added to cart.");
                        continue promptLoop;
                    } else
                        break promptLoop;

                case "cancel":
                    this.cDB.addCancellation(new Cancellation("User cancelled selecting ticket type",
                            LocalDateTime.now(), loggedInUser));
                    con.println("Taking you back to the movie menu...\n");
                    return null;

                default:
                    con.println("Invalid option. Please try again.");
                    continue promptLoop;
            }
        }

        int totalNumTickets = numChildTickets + numStudentTickets + numAdultTickets + numPensionerTickets;

        con.println("\nThank you for selecting the tickets you would like to purchase.\n");

        Map<String, Integer> tickets = new HashMap<String, Integer>();
        tickets.put("child", numChildTickets);
        tickets.put("student", numStudentTickets);
        tickets.put("adult", numAdultTickets);
        tickets.put("pensioner", numPensionerTickets);

        tickets.put("total", totalNumTickets);

        double total = 0;
        total += ticketPrice.get("child") * numChildTickets;
        total += ticketPrice.get("student") * numStudentTickets;
        total += ticketPrice.get("adult") * numAdultTickets;
        total += ticketPrice.get("pensioner") * numPensionerTickets;

        Booking booking = new Booking(selectedScreening, tickets, total);

        return this.chooseSeats(selectedScreening, booking); // calls the next step which is to book seats
    }

    /**
     * Function used to add the number of tickets after the ticket type is selected.
     * Only called in the function addTickets().
     *
     * @param ticketType The type of ticket to purchase - child, student, adult,
     *                   pensioner
     * @return the number of tickets purchased for the given ticket type (-1 on
     *         cancellation)
     */
    private int addNumberTickets(String ticketType, int remaining) {
        promptLoop: while (true) {

            con.println("Please enter the number of " + ticketType + " tickets you would like to purchase.\n");
            con.println("Enter 0 to return to the add ticket menu.");
            con.println("Or enter 'cancel' to cancel booking.");
            String selection = con.nextLine();

            int numTickets;

            if (selection.equals("cancel")) {
                this.cDB.addCancellation(new Cancellation("User cancelled selecting number of tickets",
                        LocalDateTime.now(), loggedInUser));
                con.println("Taking you back to the movie menu...\n");
                return -1;
            }

            try {
                numTickets = Integer.parseInt(selection);
            } catch (NumberFormatException e) {
                con.println("You did not enter an integer. Please try again.\n");
                continue promptLoop;
            }

            if (numTickets < 0) {
                con.println("You cannot purchase a negative number of tickets!\n");
                continue promptLoop;
            } else if (numTickets > remaining) {
                con.println("Not enough seats available in selected movie screening!\n");
                continue promptLoop;
            } else {
                return numTickets;
            }
        }
    }

    /**
     * Lets the customer allocate the seats AFTER selecting the number of tickets to
     * purchase.
     * 
     * Booking object returned should be finalised by caller.
     *
     * @param booking
     * @return null when booking failed, the booking object on success.
     */
    public Booking chooseSeats(MovieScreening screening, Booking booking) throws TimeoutException {
        con.println("------------------------------");
        con.println("Next, please select your seats.");
        int unallocatedTickets = booking.getTotalTickets();

        int frontAllocated = 0;
        int middleAllocated = 0;
        int rearAllocated = 0;

        promptLoop: while (true) {

            // if the number of unallocated tickets is 0, then break the loop because there
            // are no more unallocated
            // tickets that need to be allocated.
            if (unallocatedTickets <= 0) {
                break;
            }
            con.println("------------------------------");
            con.println("Your selected screening has the following total seat availabilities.");
            con.format("\tFront section: %d\tMiddle section: %d\tBack section: %d\n",
                    screening.getSeatsAvailableFront(), screening.getSeatsAvailableMiddle(),
                    screening.getSeatsAvailableBack());
            con.println("------------------------------");
            con.format("You have %d unallocated tickets.\n", unallocatedTickets);
            con.println("You currently have allocated the following seats:");
            con.format("\tFront section: %d\tMiddle section: %d\tBack section: %d\n", frontAllocated, middleAllocated,
                    rearAllocated);
            con.println("------------------------------");
            con.println(
                    "Please allocate your seats by first choosing front, middle or rear, and then choosing the number"
                            + " of tickets for that section.\n" + "Enter 'F' for front.\n" + "Enter 'M' for middle.\n"
                            + "Enter 'R' for rear.\n" + "Or enter 'cancel' to cancel booking.");
            String selection = con.nextLine();

            int allocatedSeats = 0;

            switch (selection) {

                case "F":
                    // front section
                    allocatedSeats = this.allocateTickets("front", unallocatedTickets,
                            screening.getSeatsAvailableFront() - frontAllocated);

                    // Cancel
                    if (allocatedSeats == -1) {
                        return null;
                    }

                    unallocatedTickets -= allocatedSeats;
                    frontAllocated += allocatedSeats;
                    break;

                case "M":
                    // middle section
                    allocatedSeats = this.allocateTickets("middle", unallocatedTickets,
                            screening.getSeatsAvailableMiddle() - middleAllocated);

                    // Cancel
                    if (allocatedSeats == -1) {
                        return null;
                    }

                    unallocatedTickets -= allocatedSeats;
                    middleAllocated += allocatedSeats;
                    break;

                case "R":
                    // rear section
                    allocatedSeats = this.allocateTickets("rear", unallocatedTickets,
                            screening.getSeatsAvailableBack() - rearAllocated);

                    // Cancel
                    if (allocatedSeats == -1) {
                        return null;
                    }

                    unallocatedTickets -= allocatedSeats;
                    rearAllocated += allocatedSeats;
                    break;

                case "cancel":
                    this.cDB.addCancellation(new Cancellation("User cancelled selecting seat allocation",
                            LocalDateTime.now(), loggedInUser));
                    con.println("Taking you back to the movie menu...\n");
                    return null;

                default:
                    con.println("Invalid option. Please try again.");
                    continue promptLoop;
            }

        }

        Map<String, Integer> seatAllocation = new HashMap<String, Integer>();
        seatAllocation.put("front", frontAllocated);
        seatAllocation.put("middle", middleAllocated);
        seatAllocation.put("rear", rearAllocated);

        booking.addAllocatedSeats(seatAllocation);

        con.println("Thank you for allocating your tickets.\n");
        con.println("The next step is to pay for your tickets.");

        if (!loggedIn) {
            loggedInUser = null; // reset the user login
            con.println("Please log in or register for an account to continue.\n");
            promptLoop: while (true) {
                con.println("------------------------------");
                con.println("Enter '1' to login");
                con.println("Enter '2' to register");
                con.println("Enter 'cancel' to cancel booking");
                con.println("------------------------------");
                String selection = con.nextLine();

                switch (selection) {

                    case "1":
                        if (this.login(true)) {
                            break promptLoop;
                        }
                        break;

                    case "2":
                        // Register as a customer
                        User newUser = this.loginDB.registerUserInterface("customer");
                        if (newUser != null) {
                            loggedInUser = newUser;
                            loggedIn = true;
                            break promptLoop;
                        }
                        break;

                    case "cancel":
                        this.cDB.addCancellation(
                                new Cancellation("User cancelled login", LocalDateTime.now(), loggedInUser));
                        con.println("Taking you back to the movie menu...\n");
                        return null;

                    default:
                        con.println("Invalid option. Please try again.");
                        continue promptLoop;
                }
            }
        }

        return this.payment(booking);
    }

    /**
     * Similar function to addNumberTickets() method, but this time it is for
     * allocating tickets to a specific section (front, middle or rear).
     *
     * @param section            The section that tickets need to be allocated for.
     * @param unallocatedTickets The number of unallocated tickets remaining (and
     *                           the maximum number of tickets the user can allocate
     *                           to this section)
     * @return The number of tickets chosen for that allocation (-1 on cancel)
     */
    private int allocateTickets(String section, int unallocatedTickets, int remaining) {

        boolean running = true;
        promptLoop: while (running) {

            con.format("\nPlease enter the number of tickets you would like to allocate to the %s section.\n", section);
            con.println("Enter 0 to return to the allocate tickets menu.");
            con.println("Or enter 'cancel' to cancel booking.");
            String selection = con.nextLine();

            int seatAllocation;

            if (selection.equals("cancel")) {
                this.cDB.addCancellation(new Cancellation("User cancelled selecting number of seat allocation",
                        LocalDateTime.now(), loggedInUser));
                con.println("Taking you back to the movie menu...\n");
                return -1;
            }

            try {
                seatAllocation = Integer.parseInt(selection);
            } catch (NumberFormatException e) {
                con.println("You did not enter an integer. Please try again.\n");
                continue promptLoop;
            }

            if (seatAllocation < 0) {
                con.println("You cannot allocate a negative number of tickets!\n");
                continue promptLoop;
            } else if (seatAllocation > unallocatedTickets) {
                con.println("You have only " + unallocatedTickets + " tickets left to allocate. Please try again.\n");
                continue promptLoop;
            } else if (seatAllocation > remaining) {
                con.println("You cannot allocate more than " + remaining + " tickets in the " + section
                        + " section. Please try again.\n");
                continue promptLoop;
            } else {
                return seatAllocation;
            }

        }

        return 0;

    }

    /**
     * Payment options for user to purchase movie tickets -- credit card or gift
     * card
     * 
     * @param booking
     * @return
     */
    private Booking payment(Booking booking) throws TimeoutException {
        boolean paid = false;
        double amountRemaining = booking.total;
        boolean running = true;
        promptLoop: while (running) {
            con.format("Amount payable: $%.2f\n", amountRemaining);
            con.println("------------------------------");
            con.println("Enter 'C' if you would like to pay with a credit card."
                    + "\nEnter 'G' if you would like to pay with a gift card."
                    + "\nEnter 'cancel' to cancel booking.\n");

            String selection = con.nextLineWithTimeout(timeoutSeconds * 1000);

            switch (selection) {

                case "C":
                    // pay using credit card

                    con.println("You have selected the option to pay using a credit card.");
                    con.println("------------------------------");
                    paid = this.creditCardPayment(booking);

                    if (!paid) {
                        // payment failed
                        this.cDB.addCancellation(
                                new Cancellation("CC Payment Failed", LocalDateTime.now(), loggedInUser));
                        con.println("Payment failed. Please try again.\n");
                        break;
                    } else {
                        // payment successful
                        break promptLoop;
                    }

                case "G":
                    // pay using gift card

                    con.println("You have selected the option to pay with a gift card.");
                    int amount = this.giftCardPayment(amountRemaining);
                    // comment.
                    amountRemaining -= amount;
                    if (amountRemaining <= 0)
                        break promptLoop;
                    else
                        continue promptLoop;
                case "cancel":
                    this.cDB.addCancellation(
                            new Cancellation("User cancelled payment", LocalDateTime.now(), loggedInUser));
                    running = false;
                    con.println("Booking has been cancelled.\n");
                    con.println("Taking you back to the movie menu...\n");
                    return null;

                default:
                    con.println("Invalid option. Please try again.");
                    continue promptLoop;
            }
        }

        con.println("Payment has been successful.");
        con.format("Your transaction ID is %d\n", config.transactionID);
        config.transactionID++;
        config.save();
        con.println("Thank you for purchasing tickets with us at HOYTSOFT movie bookings.");
        con.println("Please come again soon!");
        con.println("------------------------------");

        return booking;
    }

    /**
     * Pay with credit card option
     * 
     * @param booking
     * @return
     */
    private boolean creditCardPayment(Booking booking) throws TimeoutException {

        if (loggedInUser.hasCCDetails()) {

            String cardName = loggedInUser.getCardName();
            String cardNumber = loggedInUser.getCardNumber();

            con.println("Would you like to use your saved card details?");

            con.format("\t(Card Name: %s, Card Number: ***%s)\n", cardName, cardNumber.substring(3, 5));
            con.println("Enter 'Y' for yes.");
            con.println("Enter 'N' for no.");

            String selection = con.nextLineWithTimeout(timeoutSeconds * 1000);

            switch (selection) {

                case "Y":

                    // verify saved card details
                    boolean verified = ccDB.verifyCCDetails(cardName, cardNumber);

                    if (verified) {
                        con.println("Saved card details have been verified.\n");
                        return true;
                    } else {
                        con.println("Saved card details are invalid.");
                        con.println("Please enter new card details.\n");
                        // user is prompted the rest of this function.
                    }

                    break;

                case "N":
                    // enter new details -> rest of this function continues like normal
                    break;
            }
        }

        con.println("Please enter the card name:");
        con.println("Or enter 'cancel' to cancel booking.");
        String cardName = con.nextLineWithTimeout(timeoutSeconds * 1000);

        if (cardName.equals("cancel")) {
            this.cDB.addCancellation(new Cancellation("User cancelled payment", LocalDateTime.now(), loggedInUser));
            con.println("Taking you back to the movie menu...\n");
            return false;
        }

        con.println("\nPlease enter the card number:");
        con.println("Or enter 'cancel' to cancel booking.");
        String cardNumber = con.readPasswordWithTimeout(timeoutSeconds * 1000);

        if (cardNumber.equals("cancel")) {
            this.cDB.addCancellation(new Cancellation("User cancelled payment", LocalDateTime.now(), loggedInUser));
            con.println("Taking you back to the movie menu...\n");
            return false;
        }

        con.println("\n------------------------------");

        boolean verified = ccDB.verifyCCDetails(cardName, cardNumber);

        if (loggedIn) {
            con.println("Would you like to save your card details? (Y/N))");
            String saveCard = con.nextLineWithTimeout(timeoutSeconds * 1000);

            switch (saveCard) {

                case "Y":
                    if (loggedInUser == null) {
                        con.println("You are not logged in so we cannot save your card details.\n");
                    } else {
                        this.loggedInUser.saveCCDetails(cardName, cardNumber);
                        con.println("\nCard details are saved to your account."
                                + "\nNext time you log in to purchase movie tickets, your credit card details will autofill"
                                + ".");
                    }

                    break;

                case "N":
                    con.println("\nCard details are NOT saved to your account.");
                    break;
            }
        }

        con.println("\n------------------------------");
        return verified;
    }

    /**
     * Prompt for gift card payment. This method should give all prompts, including
     * success/failure of payment. This method should also update the gift card
     * database.
     * 
     * @return 0 if payment failed, value of gift card on success.
     */
    public int giftCardPayment(double totalAmount) throws TimeoutException {
        // Make sure to use timeout variants here and the constant (timeoutSeconds *
        // 1000) -- search this for an example.

        con.println("\nPlease enter the gift card number:");
        con.println("Or enter 'cancel' to cancel booking.");
        String giftCardNumber = con.nextLineWithTimeout(timeoutSeconds * 1000);

        if (giftCardNumber.equals("cancel")) {
            this.cDB.addCancellation(new Cancellation("User cancelled payment", LocalDateTime.now(), loggedInUser));
            con.println("Taking you back to the movie menu...\n");
            return 0;
        }

        if (!this.giftcardDB.checkGCExits(giftCardNumber)) {
            con.println("Gift card not valid.");
            return 0;
        }

        if (this.giftcardDB.checkGCUsed(giftCardNumber)) {
            con.println("Gift card already used.");
            return 0;
        }

        if (!this.giftcardDB.useGiftCard(giftCardNumber)) {
            con.println("Gift card payment failed.");
            return 0;
        }

        int amount = this.giftcardDB.getGiftCardValue(giftCardNumber);

        if (amount >= totalAmount) {
            con.println("\n------------------------------");
            con.println("You have paid the entire amount with the gift card.\n");
        } else {
            double difference = totalAmount - amount;

            con.println("\n------------------------------");
            con.println("Only $" + amount + " has been paid. $" + difference + " still remains.\n"
                    + "Please pay the rest using a credit card or another gift card.\n");

        }

        return amount;

    }

}