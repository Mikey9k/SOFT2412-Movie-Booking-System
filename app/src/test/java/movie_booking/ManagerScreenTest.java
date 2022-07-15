package movie_booking;

import org.junit.jupiter.api.Test;

import movie_booking.Databases.LoginDatabase;
import movie_booking.Databases.MovieDatabase;
import movie_booking.Databases.GiftCardDatabase;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import movie_booking.Databases.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class ManagerScreenTest {
    /**
     * Reset config files before each test.
     */
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
    public void managerScreenTestStaffAccess() {

        final String testString = "0" + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath, vc);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB, gcDB);
        CancellationDatabase cDB = new CancellationDatabase(resourcePath);
        ManagerScreen mS = new ManagerScreen(vc, loginDB, staffScreen, cDB);
        mS.run();

        assertNotNull(mS);
    }

    @Test
    public void managerScreenTestCancelReport() {

        final String testString = "2" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath, vc);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB, gcDB);
        CancellationDatabase cDB = new CancellationDatabase(resourcePath);
        ManagerScreen mS = new ManagerScreen(vc, loginDB, staffScreen, cDB);
        mS.run();

        assertNotNull(mS);
    }

    @Test
    public void managerScreenTestModifyUsersAdd() {

        int random = (new Random()).nextInt(100);

        final String testString = "1" + System.lineSeparator() + "A" + System.lineSeparator() + "S"
                + System.lineSeparator() + "NewStaffuser" + Integer.toString(random) + System.lineSeparator()
                + "password" + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);
        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath, vc);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB, gcDB);
        CancellationDatabase cDB = new CancellationDatabase(resourcePath);
        ManagerScreen mS = new ManagerScreen(vc, loginDB, staffScreen, cDB);
        mS.run();

    }

    @Test
    public void managerScreenTestModifyUsersModMenu() {

        final String testString = "1" + System.lineSeparator() + "S" + System.lineSeparator() + "3"
                + System.lineSeparator() + "D" + System.lineSeparator() + "n" + System.lineSeparator() + "S"
                + System.lineSeparator() + "3" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "S"
                + System.lineSeparator() + "T" + System.lineSeparator() + "customer" + System.lineSeparator() + "C"
                + System.lineSeparator() + "C" + System.lineSeparator() + "q" + System.lineSeparator() + "q"
                + System.lineSeparator() + "q" + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        VirtConsole vc = new VirtConsole(testIn, System.out, true);

        LoginDatabase loginDB = new LoginDatabase(resourcePath, vc);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath, vc);
        StaffScreen staffScreen = new StaffScreen(vc, loginDB, mvDB, gcDB);
        CancellationDatabase cDB = new CancellationDatabase(resourcePath);
        ManagerScreen mS = new ManagerScreen(vc, loginDB, staffScreen, cDB);
        mS.run();

    }

    @Test
    public void managerTestmodifyUser() {
        final String testString = "x" + System.lineSeparator() + "0" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "1" + System.lineSeparator() + "x" + System.lineSeparator() + "C"
                + System.lineSeparator() + "T" + System.lineSeparator() + "manager" + System.lineSeparator() + "R"
                + System.lineSeparator() + "testuser" + System.lineSeparator() + "P" + System.lineSeparator()
                + "password" + System.lineSeparator() + "D" + System.lineSeparator() + "n" + System.lineSeparator()
                + "D" + System.lineSeparator() + "Y" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        VirtConsole con = new VirtConsole(testIn, System.out, true);

        LoginDatabase loginDB = new LoginDatabase(resourcePath, con);
        GiftCardDatabase giftcardDB = new GiftCardDatabase(resourcePath, con);
        MovieDatabase mvDB = new MovieDatabase(resourcePath);

        StaffScreen staff = new StaffScreen(con, loginDB, mvDB, giftcardDB);

        CancellationDatabase cDB = new CancellationDatabase(resourcePath);
        ManagerScreen mS = new ManagerScreen(con, loginDB, staff, cDB);
        List<User> userView = loginDB.getUserList();
        List<User> userPage = userView.stream().limit(3).collect(Collectors.toList());

        mS.modifyUser(userPage);
    }
}