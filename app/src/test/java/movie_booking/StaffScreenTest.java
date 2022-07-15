package movie_booking;

import org.junit.jupiter.api.Test;

import movie_booking.Databases.LoginDatabase;
import movie_booking.Databases.MovieDatabase;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;

public class StaffScreenTest {

    @BeforeEach
    @AfterEach
    public void resetTestFiles() {
        String[] jsonFiles = { "credit_cards", "gift_cards", "movies", "users" };
        for (String f : jsonFiles) {
            try {
                byte[] buffer = new byte[2048];
                FileOutputStream out = new FileOutputStream("src/test/resources/" + f + ".json", false);
                FileInputStream in = new FileInputStream("src/test/resources/" + f + "_reset.json");
                while (in.available() > 0) {
                    int len = in.read(buffer);
                    out.write(buffer, 0, len);
                }
                out.flush();

                out.close();
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void staffScreenTestAccessPage() {

        final String testString = "1" + System.lineSeparator() + "2" + System.lineSeparator() + "C"
                + System.lineSeparator() + "g" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();


        assertNotNull(staffScreen);
    }

    @Test
    public void staffScreenTestModifyMoviesAddSuccess() {

        int random = (new Random()).nextInt(100);

        final String testString = "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + Integer.toString(random) + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator()
                + "M" + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe"
                + System.lineSeparator() + "Jane Doe as Neo, Taylor Brown as The Man" + System.lineSeparator() + "Gold"
                + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestModifyMoviesAddFail() {

        final String testString = "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe" + System.lineSeparator()
                + "Jane Doe as Neo, Taylor Brown as The Man" + System.lineSeparator() + "Gold" + System.lineSeparator()
                + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestModifyMoviesDelete() {

        final String testString = "D" + System.lineSeparator() + "f" + System.lineSeparator() + "D"
               + System.lineSeparator() + "99" + System.lineSeparator()
                + "D" + System.lineSeparator() + "1" + System.lineSeparator() + "c"
                + System.lineSeparator() + "c" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestModifyMoviesAddFailBlank() {

        final String testString = "AM" + System.lineSeparator() + "" + System.lineSeparator() + "AM"
                + System.lineSeparator() + "Sydney" + System.lineSeparator() + "" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + ""
                + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe" + System.lineSeparator()
                + "" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe" + System.lineSeparator()
                + "Jane Doe as Neo, Taylor Brown as The Man" + System.lineSeparator() + "" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe" + System.lineSeparator()
                + "Jane Doe as Neo, Taylor Brown as The Man" + System.lineSeparator() + "Gold" + System.lineSeparator()
                + "q" + System.lineSeparator() + "q";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestModifyMoviesAddFailQ() {

        final String testString = "AM" + System.lineSeparator() + "q" + System.lineSeparator() + "AM"
                + System.lineSeparator() + "Sydney" + System.lineSeparator() + "q" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "q" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "q"
                + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "q" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "q" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe" + System.lineSeparator()
                + "q" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe" + System.lineSeparator()
                + "Jane Doe as Neo, Taylor Brown as The Man" + System.lineSeparator() + "q" + System.lineSeparator() +

                "AM" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "The Matrix"
                + System.lineSeparator() + "Neo enters the matrix." + System.lineSeparator() + "M"
                + System.lineSeparator() + "08-12-2023" + System.lineSeparator() + "John Doe" + System.lineSeparator()
                + "Jane Doe as Neo, Taylor Brown as The Man" + System.lineSeparator() + "Gold" + System.lineSeparator()
                + "q" + System.lineSeparator() + "q";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestModifyMovieMenu() {
        final String testString = "M" + System.lineSeparator() + "g" + System.lineSeparator() + "M"
                + System.lineSeparator() + "100" + System.lineSeparator() + "M" + System.lineSeparator() + "1"
                + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestAddShowIncorrect() {

        final String testString = "AS" + System.lineSeparator() + "g" + System.lineSeparator() + "AS"
                + System.lineSeparator() + "100" + System.lineSeparator() + "AS" + System.lineSeparator() + "1"
                + System.lineSeparator() + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestAddShow() {

        final String testString = "AS" + System.lineSeparator() + "1" + System.lineSeparator() + "Sunday"
                + System.lineSeparator() + "11:11" + System.lineSeparator() + "AS" + System.lineSeparator() + "1"
                + System.lineSeparator() + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestAddShowInvalidDay() {

        final String testString = "AS" + System.lineSeparator() + "1" + System.lineSeparator() + "Munday"
                + System.lineSeparator() + "11:11" + System.lineSeparator() + "AS" + System.lineSeparator() + "1"
                + System.lineSeparator() + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestAddShowInvalidTime() {

        final String testString = "AS" + System.lineSeparator() + "1" + System.lineSeparator() + "Sunday"
                + System.lineSeparator() + "1111" + System.lineSeparator() + "AS" + System.lineSeparator() + "1"
                + System.lineSeparator() + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB);
        staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestDeleteMovie() {

    int random = (new Random()).nextInt(100);

    final String testString =  "DM" + System.lineSeparator() + "1" +
    System.lineSeparator() + "Sunday" + System.lineSeparator() + "23:49" +
    System.lineSeparator() + "cancel" +
    System.lineSeparator() + "cancel";
    ByteArrayInputStream testIn = new
    ByteArrayInputStream(testString.getBytes());

    String resourcePath = "src/test/resources";
    VirtConsole vc = new VirtConsole(testIn, System.out, true);
    LoginDatabase loginDB = new LoginDatabase(resourcePath,vc);
    MovieDatabase mvDB = new MovieDatabase(resourcePath);
    StaffScreen staffScreen = new StaffScreen(vc,loginDB, mvDB);
    staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestDeleteShow() {

    int random = (new Random()).nextInt(100);

    final String testString =  "DS" + System.lineSeparator() + "1" +
    System.lineSeparator() + "Sunday" + System.lineSeparator() + "23:49" +
    System.lineSeparator() + "cancel" +
    System.lineSeparator() + "cancel";
    ByteArrayInputStream testIn = new
    ByteArrayInputStream(testString.getBytes());

    String resourcePath = "src/test/resources";
    VirtConsole vc = new VirtConsole(testIn, System.out, true);
    LoginDatabase loginDB = new LoginDatabase(resourcePath,vc);
    MovieDatabase mvDB = new MovieDatabase(resourcePath);
    StaffScreen staffScreen = new StaffScreen(vc,loginDB, mvDB);
    staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestDeleteShowInvalidTime() {

    int random = (new Random()).nextInt(100);

    final String testString =  "DS" + System.lineSeparator() + "1" +
    System.lineSeparator() + "Sunday" + System.lineSeparator() + "2349" +
    System.lineSeparator() + "cancel" +
    System.lineSeparator() + "cancel";
    ByteArrayInputStream testIn = new
    ByteArrayInputStream(testString.getBytes());

    String resourcePath = "src/test/resources";
    VirtConsole vc = new VirtConsole(testIn, System.out, true);
    LoginDatabase loginDB = new LoginDatabase(resourcePath,vc);
    MovieDatabase mvDB = new MovieDatabase(resourcePath);
    StaffScreen staffScreen = new StaffScreen(vc,loginDB, mvDB);
    staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestDeleteShowInvalidDay() {

    int random = (new Random()).nextInt(100);

    final String testString =  "DS" + System.lineSeparator() + "1" +
    System.lineSeparator() + "MonTuesDay" + System.lineSeparator() + "23:49" +
    System.lineSeparator() + "cancel" +
    System.lineSeparator() + "cancel";
    ByteArrayInputStream testIn = new
    ByteArrayInputStream(testString.getBytes());

    String resourcePath = "src/test/resources";
    VirtConsole vc = new VirtConsole(testIn, System.out, true);
    LoginDatabase loginDB = new LoginDatabase(resourcePath,vc);
    MovieDatabase mvDB = new MovieDatabase(resourcePath);
    StaffScreen staffScreen = new StaffScreen(vc,loginDB, mvDB);
    staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestBookingReport() {

    int random = (new Random()).nextInt(100);

    final String testString = "MR" + 
    System.lineSeparator() + "cancel" +
    System.lineSeparator() + "cancel";
    ByteArrayInputStream testIn = new
    ByteArrayInputStream(testString.getBytes());

    String resourcePath = "src/test/resources";
    VirtConsole vc = new VirtConsole(testIn, System.out, true);
    LoginDatabase loginDB = new LoginDatabase(resourcePath,vc);
    MovieDatabase mvDB = new MovieDatabase(resourcePath);
    StaffScreen staffScreen = new StaffScreen(vc,loginDB, mvDB);
    staffScreen.adminMenu();

    }

    @Test
    public void staffScreenTestMovieReport() {

    int random = (new Random()).nextInt(100);

    final String testString = "BR" + 
    System.lineSeparator() + "cancel" +
    System.lineSeparator() + "cancel";
    ByteArrayInputStream testIn = new
    ByteArrayInputStream(testString.getBytes());

    String resourcePath = "src/test/resources";
    VirtConsole vc = new VirtConsole(testIn, System.out, true);
    LoginDatabase loginDB = new LoginDatabase(resourcePath,vc);
    MovieDatabase mvDB = new MovieDatabase(resourcePath);
    StaffScreen staffScreen = new StaffScreen(vc,loginDB, mvDB);
    staffScreen.adminMenu();

    }

}