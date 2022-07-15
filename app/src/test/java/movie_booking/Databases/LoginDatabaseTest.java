
package movie_booking.Databases;

import java.io.ByteArrayInputStream;
import movie_booking.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;



public class LoginDatabaseTest {

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
    public void LoginDatabaseTestRegisterCustomerSuccess() {
        final String testString = "randomUser" + System.lineSeparator() + "password"
                + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LoginDatabase TestLDB = new LoginDatabase(resourcePath, new VirtConsole(testIn, System.out, true));
        assertNotNull(TestLDB.registerUserInterface("customer"));

    }

    @Test
    public void LoginDatabaseTestRegisterCustomerInvalidUsername() {
        final String testString = "" + System.lineSeparator() + "password"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LoginDatabase TestLDB = new LoginDatabase(resourcePath, new VirtConsole(testIn, System.out, true));
        assertNull(TestLDB.registerUserInterface("customer"));

    }


    @Test
    public void LoginDatabaseTestRegisterCustomerInvalidPass() {
        final String testString = "userTest" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LoginDatabase TestLDB = new LoginDatabase(resourcePath, new VirtConsole(testIn, System.out, true));
        assertNull(TestLDB.registerUserInterface("customer"));

    }

    @Test
    public void LoginDatabaseTestRegisterCustomerInvalidPass2() {
        final String testString = "userTest" + System.lineSeparator() + ""
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LoginDatabase TestLDB = new LoginDatabase(resourcePath, new VirtConsole(testIn, System.out, true));
        assertNull(TestLDB.registerUserInterface("customer"));

    }
    @Test
    public void LoginDatabaseTestRegisterManagerSuccess() {

        final String testString = "randomManager" + System.lineSeparator() + "password"
                + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LoginDatabase TestLDB = new LoginDatabase(resourcePath, new VirtConsole(testIn, System.out, true));
        assertNotNull(TestLDB.registerUserInterface("manager"));

    }

    @Test
    public void LoginDatabaseTestRegisterStaffSuccess() {

        final String testString = "randomStaff"+ System.lineSeparator() + "password"
                + System.lineSeparator() + "q" + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LoginDatabase TestLDB = new LoginDatabase(resourcePath, new VirtConsole(testIn, System.out, true));
        assertNotNull(TestLDB.registerUserInterface("staff"));

    }

    @Test
    public void LoginDatabaseTestRegisterFail() {

        final String testString = "admin" + System.lineSeparator() + "1234" + System.lineSeparator() + "q"
                + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LoginDatabase TestLDB = new LoginDatabase(resourcePath, new VirtConsole(testIn, System.out, true));
        assertNull(TestLDB.registerUserInterface("manager"));

    }
}