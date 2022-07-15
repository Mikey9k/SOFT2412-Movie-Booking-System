package movie_booking;

import java.util.List;
import java.util.stream.Collectors;

import movie_booking.Databases.*;
import movie_booking.Databases.User;

public class ManagerScreen {

    static final int USERS_PER_PAGE = 5;

    VirtConsole con;
    LoginDatabase loginDB;
    StaffScreen staffScreen;
    MovieDatabase mvDB;
    CancellationDatabase cDB;

    StaffScreen manager = null;

    public ManagerScreen(VirtConsole con, LoginDatabase loginDB, StaffScreen staffScreen, CancellationDatabase cDB) {
        this.con = con;
        this.loginDB = loginDB;
        this.staffScreen = staffScreen;
        this.cDB = cDB;

    }

    /**
     * Main event loop for cinema staff.
     * 
     * @return A signal: true for should retry, false for should exit.
     */
    public boolean run() {
        con.println("\nCinema Manager Menu:");
        con.println("-------------------------------");
        con.println("Enter 0 to enter staff menu.");
        con.println("Enter 1 to add/remove/modify users.");
        con.println("Enter 2 to generate cancellation report.");
        con.println("Enter q or cancel to logout.");
        con.println("-------------------------------");
        con.print("Selected option: ");

        String input = con.nextLine();
        while (true) {
            switch (input) {
                case "0":
                this.staffScreen.adminMenu();
                return true;

                case "1":
                    manageUsers();
                    return true;

                case "2":
                    this.cDB.cancelReport(con);
                    return true;

                case "cancel":
                case "q":
                    con.println("You have been logged out.");
                    return false;

                default:
                    con.println("Invalid option. Please try again.");
                    con.print("Selected option: ");
                    input = con.nextLine();
            }
        }
    }

    public void manageUsers() {
        int currentPage = 1;
        mainLoop: while (true) {
            List<User> userView = loginDB.getUserList();
            int maxPage = (int) Math.ceil(userView.size() / (float) USERS_PER_PAGE);
            if (currentPage > maxPage) {
                currentPage = maxPage > 0 ? maxPage : 1;
            }
            List<User> userPage = userView.stream().skip((currentPage - 1) * USERS_PER_PAGE).limit(USERS_PER_PAGE)
                    .collect(Collectors.toList());

            con.println("\n");
            for (int i = 0; i < userPage.size(); i++) {
                con.println("----------------------");
                con.println(userPage.get(i).toDetails());
                con.format("\t<User #%d>\n", i + 1);
            }
            con.println("----------------------");

            con.format("Page %d of %d.\n", currentPage, maxPage);
            con.println("Enter A to (A)dd a new user.");
            con.println("Enter S to (S)elect a user to modify or remove.");
            con.println("Enter a number to jump to that page.");
            con.println("Enter q to abort the operation.");
            con.println("-------------------------");
            promptLoop: while (true) {
                con.print("Selected option: ");
                String input = con.nextLine();

                try {
                    int newPage = Integer.parseInt(input);
                    if (newPage < 1 || newPage > maxPage) {
                        con.println("Invalid page number.");
                        continue promptLoop;
                    }
                    currentPage = newPage;
                    continue mainLoop;
                } catch (NumberFormatException e) {
                }

                switch (input) {
                    case "A":
                        addUser();
                        continue mainLoop;
                    case "S":
                        modifyUser(userPage);
                        continue mainLoop;
                    case "q":
                        con.println("Operation aborted.");
                        return;
                }

            }
        }
    }

    public void generateCancellationReport() {
        con.println("Cancellation report functionality in development!");

    }

    public void addUser() {
        
        addLoop: while (true) {
            con.println("Please select the type of user to add:");
            con.println("Enter 'S' for (S)taff");
            con.println("Enter 'M' for (M)anager");
            con.println("Enter 'C' for (C)ustomer");
            con.println("Enter 'q' to abort operation'");
            con.println("Selected option: ");

            switch (con.nextLine()) {
                case "S":
                    loginDB.registerUserInterface("staff");
                    return;
                case "M":
                    loginDB.registerUserInterface("manager");
                    break addLoop;
                case "C":
                    loginDB.registerUserInterface("customer");
                    break addLoop;
                case "q":
                    con.println("Operation aborted.");
                    return;
                default:
                    con.println("Invalid option.");
                    con.print("Selected option: ");
                    continue addLoop;
            }
        }

            loginDB.save();
            con.println("The operation has been completed successfully.");
            return;
        }


    public void modifyUser(List<User> currentPage) {
        User selectedUser = null;
        selectLoop: while (true) {
            con.println("Please select the user to modify:");
            con.println("Or enter 'q' to abort operation.");

            String input = con.nextLine();

            if (input.equals("q")) {
                con.println("Operation aborted.");
                return;
            }

            int index;
            try {
                index = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                con.println("Invalid option.");
                continue selectLoop;
            }
            if (index < 1 || index > currentPage.size()) {
                con.println("Invalid option.");
                continue selectLoop;
            }

            selectedUser = currentPage.get(index - 1);
            break;
        }

        optionLoop: while (true) {
            con.println("Currently selected user:");
            con.println(selectedUser.toDetails());
            con.println("-------------------------");
            con.println("Enter D to (D)elete this user.");
            con.println("Enter R to (R)ename this user");
            con.println("Enter P to reset (P)assword.");
            con.println("Enter T to change the (T)ype.");
            con.println("Enter C to (C)lear the saved payment details.");
            con.println("Enter 'q' to abort operation.");
            con.println("-------------------------");
            modifyLoop: while (true) {
                con.print("Selected option: ");
                switch (con.nextLine()) {
                    case "D":
                        con.print("Are you sure you want to delete this user (Y/N)? ");
                        if (con.nextLine().equals("Y")) {
                            loginDB.getUserList().remove(selectedUser);
                            loginDB.save();
                            con.println("The operation has been completed successfully.");
                            return;
                        }
                        con.println("Deletion aborted.");
                        break modifyLoop;
                    case "R":
                        con.print("Enter the new username: ");
                        selectedUser.setUsername(con.nextLine());
                        break modifyLoop;
                    case "P":
                        con.print("Enter the new password: ");
                        selectedUser.setPassword(con.readPassword());
                        break modifyLoop;
                    case "T":
                        con.println("Enter the new user type: ");
                        con.println("\t(M) for manager, (S) for staff, (C) for customer.");
                        selectedUser.setType(con.nextLine());
                        break modifyLoop;
                    case "C":
                        selectedUser.containsCCDetails(false);
                        break modifyLoop;
                    case "q":
                        con.println("Operation aborted.");
                        return;
                    default:
                        con.println("Invalid option.");
                        con.print("Selected option: ");
                        continue modifyLoop;
                }

            }
            loginDB.save();
            con.println("The operation has been completed successfully.");
            continue optionLoop;
        }
    }

}
