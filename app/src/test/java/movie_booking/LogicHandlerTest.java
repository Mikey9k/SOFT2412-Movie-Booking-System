package movie_booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class LogicHandlerTest {

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
    public void logicHandlerTestLoginSuccess() {

        final String testString = "admin" + System.lineSeparator() + "1234" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertTrue(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginStaffSuccess() {

        final String testString = "staff" + System.lineSeparator() + "1234" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertTrue(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginFailPassword() {

        final String testString = "admin" + System.lineSeparator() + "1235" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertFalse(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginUsernameCancel() {

        final String testString = "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertFalse(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginPasswordCancel() {

        final String testString = "admin" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertFalse(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginFailBlank() {

        final String testString = "" + System.lineSeparator() + "admin" + System.lineSeparator() + ""
                + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertFalse(TestLH.login());
        assertFalse(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginBrowseChangePage() {

        final String testString = "customeruser" + System.lineSeparator() + "1234" + System.lineSeparator() + "2"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertTrue(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginBrowseSelect() {

        final String testString = "customeruser" + System.lineSeparator() + "1234" + System.lineSeparator() + "2"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "N"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertTrue(TestLH.login());

    }

    @Test
    public void logicHandlerTestLoginBrowseSelectCancel() {

        final String testString = "customeruser" + System.lineSeparator() + "1234" + System.lineSeparator() + "2"
                + System.lineSeparator() + "A" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        assertTrue(TestLH.login());
    }

    @Test
    public void logicHandlerTestBrowseSelectCancel() {

        final String testString = "3" + System.lineSeparator() + "M" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();
    }

    @Test
    public void logicHandlerTestBrowseSelectInvalid() {

        final String testString = "3" + System.lineSeparator() + "M" + System.lineSeparator() + "100000"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();
    }

    @Test
    public void logicHandlerTestRegistrationSuccess() {

        final String testString = "2" + System.lineSeparator() + "testuser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();
    }

    @Test
    public void logicHandlerTestInvalidInput() {

        final String testString = "x" + System.lineSeparator() + "3" + System.lineSeparator() + "M" 
                + System.lineSeparator() + "1" + System.lineSeparator() + "g" + System.lineSeparator() 
                + "N" + System.lineSeparator() + "C" + System.lineSeparator() + "M" + System.lineSeparator() + "f" 
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" 
                + System.lineSeparator() + "Sunday" + System.lineSeparator() + "23:49" + System.lineSeparator() + "S"
                + System.lineSeparator() + "-1" + System.lineSeparator() + "1" + System.lineSeparator() + "C"
                + System.lineSeparator() + "1" + System.lineSeparator() + "A" + System.lineSeparator() + "l"
                + System.lineSeparator() + "1" + System.lineSeparator() + "P" + System.lineSeparator() + "1000"
                + System.lineSeparator() + "1" + System.lineSeparator() + "F" + System.lineSeparator() + "F"
                + System.lineSeparator() + "-2" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "g"
                + System.lineSeparator() + "50000" + System.lineSeparator() + "1" + System.lineSeparator() + "1"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "2" + System.lineSeparator()
                + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        // assertTrue(TestLH.run());
        TestLH.run();

    }

    @Test
    public void logicHandlerTestInvalidInput2() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() ;

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBook() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "S"
                + System.lineSeparator() + "1" + System.lineSeparator() + "A" + System.lineSeparator() + "1"
                + System.lineSeparator() + "P" + System.lineSeparator() + "1" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "2"+ System.lineSeparator() + "C"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator()+ "cancel" + System.lineSeparator()+ "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        // assertTrue(TestLH.run());
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookCancelDay() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "cancel"  
                + System.lineSeparator() + "q" + System.lineSeparator() + "q" + System.lineSeparator() + "cancel"
                + System.lineSeparator()+ "cancel" + System.lineSeparator()+ "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    
    @Test
    public void logicHandlerTestRunBookCancelTime() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "cancel"
                + System.lineSeparator() + "C" + System.lineSeparator() + "1000" + System.lineSeparator() + "S"
                + System.lineSeparator() + "1000" + System.lineSeparator() + "A" + System.lineSeparator() + "1000"
                + System.lineSeparator() + "P" + System.lineSeparator() + "1000" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "2"+ System.lineSeparator() + "C"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator()+ "cancel" + System.lineSeparator()+ "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookCCPaymentNewSave() {

        final String testString = "1" + System.lineSeparator() + "user" + System.lineSeparator() + "password"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "S"
                + System.lineSeparator() + "1" + System.lineSeparator() + "A" + System.lineSeparator() + "1"
                + System.lineSeparator() + "P" + System.lineSeparator() + "1" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "2"
                + System.lineSeparator() + "C" + System.lineSeparator() + "John" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "Y" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        // assertTrue(TestLH.run());
        TestLH.run();

    }



    @Test
    public void logicHandlerTestRunBookCCPaymentNewDontSave() {

        final String testString = "1" + System.lineSeparator() + "user" + System.lineSeparator() + "password"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "S"
                + System.lineSeparator() + "1" + System.lineSeparator() + "A" + System.lineSeparator() + "1"
                + System.lineSeparator() + "P" + System.lineSeparator() + "1" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "2"
                + System.lineSeparator() + "C" + System.lineSeparator() + "John" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "N" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookCCPaymentRemember() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "1" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1" + System.lineSeparator() + "C"
                + System.lineSeparator() + "Y" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }


    @Test
    public void logicHandlerTestRunBookCCPaymentNewCancel() {

        final String testString = "1" + System.lineSeparator() + "user" + System.lineSeparator() + "password"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "S"
                + System.lineSeparator() + "1" + System.lineSeparator() + "A" + System.lineSeparator() + "1"
                + System.lineSeparator() + "P" + System.lineSeparator() + "1" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "2"
                + System.lineSeparator() + "C" + System.lineSeparator() + "John" + System.lineSeparator() + "cancel"
               + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookGC() {

        final String testString = "1" + System.lineSeparator() + "user" + System.lineSeparator() + "password"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "S"
                + System.lineSeparator() + "1" + System.lineSeparator() + "A" + System.lineSeparator() + "1"
                + System.lineSeparator() + "P" + System.lineSeparator() + "1" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "2"
                + System.lineSeparator() + "G" + System.lineSeparator() + "GC161966459913396" + System.lineSeparator() + "C" + System.lineSeparator() +  "John" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "Y" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookGCFullTicketPaid() {

        final String testString = "1" + System.lineSeparator() + "user" + System.lineSeparator() + "password"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "1" + System.lineSeparator()  + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1" + System.lineSeparator()
                + System.lineSeparator() + "G" + System.lineSeparator() + "GC161966459913396" + System.lineSeparator() + "C" + System.lineSeparator() +  "John" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "Y" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookGCCancel() {

        final String testString = "1" + System.lineSeparator() + "user" + System.lineSeparator() + "password"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "S"
                + System.lineSeparator() + "1" + System.lineSeparator() + "A" + System.lineSeparator() + "1"
                + System.lineSeparator() + "P" + System.lineSeparator() + "1" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "2" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "R" + System.lineSeparator() + "2"
                + System.lineSeparator() + "G" + System.lineSeparator() + "cancel" + System.lineSeparator() 
                + System.lineSeparator() + "Y" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel";

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookCancel() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookAddSeatCancel() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookSeatOverUnderAllocationIncorrectInput() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "g" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "Y" +  System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "0149" + System.lineSeparator() + "23:49" + System.lineSeparator() + "C"
                + System.lineSeparator() + "2" + System.lineSeparator() + "S" + System.lineSeparator() + "-1"
                + System.lineSeparator() + "1" + System.lineSeparator() + "a" + System.lineSeparator() + "A"
                + System.lineSeparator() + "777" + System.lineSeparator() + "P" + System.lineSeparator() + "g"
                + System.lineSeparator() + "1" + System.lineSeparator() + "F" + System.lineSeparator() + "F"
                + System.lineSeparator() + "2000" + System.lineSeparator() + "C" + System.lineSeparator() + "Y"
                + System.lineSeparator() + "R" + System.lineSeparator() + "2" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator()
                + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookSeatsSoldOut() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" 
                + System.lineSeparator() + "1" + System.lineSeparator() + "Y" +  System.lineSeparator() + "Sunday"  +
                System.lineSeparator() +  "23:49" + System.lineSeparator() + "C"
                + System.lineSeparator() + "99" + System.lineSeparator() + System.lineSeparator() + "S" + System.lineSeparator() + "1"
                + System.lineSeparator() + "S" + System.lineSeparator() + "1"
                + System.lineSeparator() + "S" + System.lineSeparator() + "1"
                + System.lineSeparator() + "S" + System.lineSeparator() + "1"
                + System.lineSeparator() + "S" + System.lineSeparator() + "1"
                + System.lineSeparator() + "S" + System.lineSeparator() + "1"
                + System.lineSeparator() + "S" + System.lineSeparator() + "1" + System.lineSeparator() + "F" + System.lineSeparator() + "95"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"
                + System.lineSeparator() + "F" + System.lineSeparator() + "1"

                + System.lineSeparator() + "C" + System.lineSeparator() + "Y" + System.lineSeparator()
                + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

        ByteArrayInputStream testIn2 = new ByteArrayInputStream(testString.getBytes());

        LogicHandler TestLH2 = new LogicHandler(new VirtConsole(testIn2, System.out, true), resourcePath);
        TestLH2.run();

    }

    @Test
    public void logicHandlerTestRunBookSeatAllocationInvalidInput() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "F"
                + System.lineSeparator() + "o" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBookSeatAllocationCancel() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "M" + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday"  +
                System.lineSeparator() + "23:49"
                + System.lineSeparator() + "C" + System.lineSeparator() + "2" + System.lineSeparator() + "F"
                + System.lineSeparator() + "F" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBrowseFilterLocation() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "L" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "L"
                + System.lineSeparator() + "Melbourne" + System.lineSeparator() + "L" + System.lineSeparator()
                + "Adelaide" + System.lineSeparator() + "L" + System.lineSeparator() + "cancel" + System.lineSeparator()
                + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    
    @Test
    public void logicHandlerTestRunBookSoldout() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "L" + System.lineSeparator() + "Hobart" + System.lineSeparator() + "M"
                + System.lineSeparator() + "1" + System.lineSeparator() + "Y" + System.lineSeparator() + "Sunday" + System.lineSeparator() + "23:49" + System.lineSeparator()
                + "G" + System.lineSeparator() +  "N" + System.lineSeparator() +  
                "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBrowseFilterScreen() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "S" + System.lineSeparator() + "Bronze" + System.lineSeparator() + "S"
                + System.lineSeparator() + "Silver" + System.lineSeparator() + "S" + System.lineSeparator() + "Gold"
                + System.lineSeparator() + "S" + System.lineSeparator() + "Platinum" + System.lineSeparator() + "C"
                + System.lineSeparator() + "S" + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

    @Test
    public void logicHandlerTestRunBrowseFilterScreenIncorrectInputs() {

        final String testString = "1" + System.lineSeparator() + "customeruser" + System.lineSeparator() + "1234"
                + System.lineSeparator() + "10" + System.lineSeparator() + "1" + System.lineSeparator() + "10"
                + System.lineSeparator() + "L" + System.lineSeparator() + "Hobart" + System.lineSeparator() + "g"
                + System.lineSeparator() + "S" + System.lineSeparator() + "S" + System.lineSeparator() + "Platinum"
                + System.lineSeparator() + "cancel" + System.lineSeparator() + "cancel" + System.lineSeparator();

        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        LogicHandler TestLH = new LogicHandler(new VirtConsole(testIn, System.out, true), resourcePath);
        TestLH.run();

    }

}
