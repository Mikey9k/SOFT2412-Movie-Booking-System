package movie_booking.Databases;

import java.io.ByteArrayInputStream;
import movie_booking.*;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;


public class GiftCardDatabaseTest {
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
   public void testLoad() {
       final String testString = "";
       ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

       String resourcePath = "src/test/resources";

       GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath,new VirtConsole(testIn, System.out, true) );
       assertNotNull(gcDB);
       gcDB.save();
   }

   @Test
   public void addGCTestValid20() {

       Integer random = ((new Random()).nextInt(10));
       Integer random1 = ((new Random()).nextInt(10));
       Integer random2 = ((new Random()).nextInt(10));
       Integer random3 = ((new Random()).nextInt(10));

       final String testString = "20" + System.lineSeparator();
       ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

       String resourcePath = "src/test/resources";
       GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath,new VirtConsole(testIn, System.out, true) );
       gcDB.addGiftCardInterface();

   }

   //remove RNG when @before added to tests
   @Test
   public void addGCTestInvalid1234() {


       final String testString = "1234" + System.lineSeparator() + "50" + System.lineSeparator();
       ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

       String resourcePath = "src/test/resources";
       GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath,new VirtConsole(testIn, System.out, true) );
       gcDB.addGiftCardInterface();

   }

   @Test
   public void addGCTestInvalidInteger() {


       final String testString = "abc" + System.lineSeparator() + "50" + System.lineSeparator();
       ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

       String resourcePath = "src/test/resources";
       GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath,new VirtConsole(testIn, System.out, true) );
       gcDB.addGiftCardInterface();

   }

   @Test
   public void addGCTestCancel() {


       final String testString = "cancel" + System.lineSeparator();
       ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

       String resourcePath = "src/test/resources";
       GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath,new VirtConsole(testIn, System.out, true) );
       gcDB.addGiftCardInterface();

   }

    @Test
    public void addGCTestBlank100() {

        final String testString = "" + System.lineSeparator() + "100" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";
        GiftCardDatabase gcDB = new GiftCardDatabase(resourcePath,new VirtConsole(testIn, System.out, true) );
        gcDB.addGiftCardInterface();

    }

}
