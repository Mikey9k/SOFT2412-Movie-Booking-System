package movie_booking;

import movie_booking.Databases.LoginDatabase;
import movie_booking.Databases.MovieDatabase;

import java.time.LocalDate;


import java.time.format.*;
import movie_booking.Databases.*;

public class StaffScreen {

    static final int USERS_PER_PAGE = 5;

    VirtConsole con;
    LoginDatabase loginDB;
    MovieDatabase mvDB;
    GiftCardDatabase gcDB;

    StaffScreen manager = null;

    public StaffScreen(VirtConsole con, LoginDatabase loginDB, MovieDatabase mvDB, GiftCardDatabase gcDB) {
        this.con = con;
        this.loginDB = loginDB;
        this.mvDB = mvDB;
        this.gcDB = gcDB;
    }

    public StaffScreen(VirtConsole con, LoginDatabase loginDB, MovieDatabase mvDB) {
        this.con = con;
        this.loginDB = loginDB;
        this.mvDB = mvDB;

    }


    /**
     * Display admin user options
     * 
     * @return true to exit
     */
    public boolean adminMenu() {
        mvDB.reset();

        boolean adminActive = true;
        promptLoop: while (adminActive) {
            con.println("");
            mvDB.setTimeFilter(null);
            mvDB.displayPage(con);
            mvDB.displayFilters(con);
            con.println("------------------------------");
            con.println("Enter 'AM' to (A)dd (M)ovie");
            con.println("Enter 'M' to (M)odify Movie details");
            con.println("Enter 'DM' to (D)elete (M)ovie");
            con.println("Enter 'AS' to (A)dd (S)how");
            con.println("Enter 'DS' to (D)elete (S)how");
            con.println("Enter 'AG' to (A)dd (G)iftcard");
            con.println("Enter 'MR' to generate the (M)ovie (R)eport");
            con.println("Enter 'BR' to generate the (B)ooking (R)eport");
            con.println("------------------------------");
            con.println("Enter 'q' or 'cancel' to logout.");
            con.println("------------------------------");
            String selection = con.nextLine();

            try {
                int page = Integer.parseInt(selection);
                if (page <= 0 || page > mvDB.getPageCount()) {
                    con.println("Invalid selection.");
                    continue promptLoop;
                }
                mvDB.setPage(page);
                continue promptLoop;
            } catch (NumberFormatException e) {
            }

            switch (selection) {

                case "AM":
                    this.insertMovie();
                    break;

                case "M":
                    // Select movie by movie option number
                    int optionNumber;

                    con.println("Please enter a movie option number to modify:");
                    con.println("Or enter 'q' to abort operation.");
                    selection = con.nextLine();

                    if (selection.equals("q")) {
                        con.println("Operation aborted.");
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

                    mvDB.editMovieDetails(con);

                    return false;

                case "DM":
                    // Select movie by movie option number
                    int optionNumber3;

                    con.println("Please enter a movie option number to delete:");
                    con.println("Or enter 'q' to abort operation.");
                    selection = con.nextLine();

                    if (selection.equals("q")) {
                        con.println("Operation aborted.");
                        break;
                    }

                    try {
                        optionNumber3 = Integer.parseInt(selection);
                    } catch (NumberFormatException e) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    if (mvDB.selectMovie(optionNumber3) == false) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    if (this.mvDB.deleteMovie()) {
                        con.println("Movie Deleted!");
                        continue promptLoop;

                    } else {
                        con.println("Movie Not Deleted!");
                        continue promptLoop;
                    }


                // In progress
                case "AS":
                    // Select movie by movie option number
                    int optionNumber2;

                    con.println("Please enter a movie option number:");
                    con.println("Or enter 'q' to abort operation.");
                    selection = con.nextLine();

                    if (selection.equals("q")) {
                        con.println("Operation aborted.");
                        break;
                    }

                    try {
                        optionNumber2 = Integer.parseInt(selection);
                    } catch (NumberFormatException e) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    if (mvDB.selectMovie(optionNumber2) == false) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    if (this.mvDB.addShow(con)) {
                        con.println("Show Added!");
                        con.println("Press Enter to continue.");
                        con.nextLine();
                        continue promptLoop;

                    } else {
                        con.println("Show Not Added!");
                        con.println("Press Enter to continue.");
                        con.nextLine();
                        continue promptLoop;
                    }

                case "DS":
                    // Select movie by movie option number
                    int optionNumber4;

                    con.println("Please enter a movie option number to delete:");
                    con.println("Or enter 'q' to abort operation.");
                    selection = con.nextLine();

                    if (selection.equals("q")) {
                        con.println("Operation aborted.");
                        break;
                    }

                    try {
                        optionNumber4 = Integer.parseInt(selection);
                    } catch (NumberFormatException e) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    if (mvDB.selectMovie(optionNumber4) == false) {
                        con.println("Invalid movie option number.");
                        continue promptLoop;
                    }

                    if (this.mvDB.deleteShow(con)) {
                        con.println("Show Deleted!");
                        continue promptLoop;

                    } else {
                        con.println("Show Not Deleted!");
                        continue promptLoop;
                    }


                case "AG":
                    this.gcDB.addGiftCardInterface();
                    continue promptLoop;

                case "MR":
                    this.mvDB.moviesReport(con);
                    continue promptLoop;
                
                case "BR":
                    this.mvDB.bookingsReport(con);
                    continue promptLoop;
                case "q":

                case "cancel":
                    con.println("Logout successful.");
                    con.println("You have been logged out.");
                    return false;

                default:
                    con.println("Invalid option. Please try again.");
                    continue promptLoop;
            }
        }
        return false;
    }

    /**
     * Add a new movie to database
     *
     * @return true for successful insertion, false if unsuccessful
     */
    public boolean insertMovie() {
        String cinema, name, synopsis, classif, director, cast, screenSize;
        LocalDate releaseDate;

        while (true) {
            con.println("Please enter Cinema location: ");
            con.println("Or enter 'q' to abort operation.");
            
            cinema = con.nextLine();

            if (cinema.equals("q")) {
                con.println("Operation aborted.");
                return false;
            }

            if (cinema.equals("")) {
                con.println("Invalid location.");
                continue;
            }

            break;
        }

        while (true) {
            con.println("Please enter Movie Name: ");
            con.println("Or enter 'q' to abort operation.");

            name = con.nextLine();

            if (name.equals("q")) {
                con.println("Operation aborted.");
                return false;
            }

            if (name.equals("")) {
                con.println("Movie Name blank, Insertion Failed");
                continue;
            }
            break;
        }

        while (true) {
            con.println("Please enter Synopsis: ");
            con.println("Or enter 'q' to abort operation.");

            synopsis = con.nextLine();

            if (synopsis.equals("")) {
                con.println("Synopsis blank, Insertion Failed");
                continue;
            }
            break;
        }

        while (true) {
            con.println("Please enter Classfication: ");
            con.println("Or enter 'q' to abort operation.");

            classif = con.nextLine();

            if (classif.equals("q")) {
                con.println("Operation aborted.");
                return false;
            }

            if (classif.equals("")) {
                con.println("Location blank, Insertion Failed");
                continue;
            }
            break;
        }

        while (true) {
            con.println("Please enter Release Date (DD-MM-YYYY): ");
            con.println("Or enter 'q' to abort operation.");

            String dateString = con.nextLine();

            if (dateString.equals("q")) {
                con.println("Operation aborted.");
                return false;
            }

            if (dateString.equals("")) {
                con.println("Location blank, Insertion Failed");
                continue;
            }
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                releaseDate = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                continue;
            }
            break;
        }

        while (true) {

            con.println("Please enter Director: ");
            con.println("Or enter 'q' to abort operation.");
            
            director = con.nextLine();

            if (director.equals("q")) {
                con.println("Operation aborted.");
                return false;
            }

            if (director.equals("")) {
                con.println("Director blank, Insertion Failed");
                continue;
            }
            break;
        }

        while (true) {
            con.println("Please enter Cast: ");
            con.println("Or enter 'q' to abort operation.");

            cast = con.nextLine();

            if (cast.equals("q")) {
                con.println("Operation aborted.");
                return false;
            }

            if (cast.equals("")) {
                con.println("Location blank, Insertion Failed");
                continue;
            }
            break;
        }

        while (true) {
            con.println("Please enter Screen Size: ");
            con.println("Or enter 'q' to abort operation.");
            screenSize = con.nextLine();

            if (screenSize.equals("q")) {
                con.println("Operation aborted.");
                return false;
            }

            if (screenSize.equals("")) {
                con.println("Location blank, Insertion Failed");
                continue;
            }
            break;
        }

        if (this.mvDB.addMovie(cinema, name, synopsis, classif, releaseDate, director, cast, screenSize)) {
            con.println("Insertion Succesful!");
            return true;

        } else {
            con.println("Insertion Failed!");
            return false;
        }
    }


}
