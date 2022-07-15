package movie_booking.Databases;

import java.io.ByteArrayInputStream;
import movie_booking.*;


import java.time.LocalDate;
import java.util.*;



import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;


public class MovieDatabaseTest {


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
    public void testUpdateDetails() {
        final String testString = "" + System.lineSeparator()+  "1" + System.lineSeparator() + "Sydney" + System.lineSeparator() + "2"
                + System.lineSeparator() + "The Matrix" + System.lineSeparator() + "3" + System.lineSeparator() + "Neo enters the matrix."
                + System.lineSeparator() + "4" + System.lineSeparator() + "M" + System.lineSeparator() + "5" + System.lineSeparator()
                +   "2000-11-25"
                + System.lineSeparator() + "6" + System.lineSeparator() + "John Doe" + System.lineSeparator() + "7"
                + System.lineSeparator() + "Jane Doe as Neo, Taylor Brown as The Man" + System.lineSeparator() + "8" + System.lineSeparator() + "Gold"
                + System.lineSeparator() + "q";
        ByteArrayInputStream testIn = new ByteArrayInputStream(testString.getBytes());

        String resourcePath = "src/test/resources";

        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        mvDB.setPage(1);
        mvDB.selectMovie(1);

        mvDB.editMovieDetails(new VirtConsole(testIn, System.out, true));
    }

    
    @Test
    public void addMovieTest() {

        String resourcePath = "src/test/resources";

        MovieDatabase mvDB = new MovieDatabase(resourcePath);
        LocalDate newReleaseDate = LocalDate.parse("2000-11-25");
        int randint = (new Random()).nextInt(1000);
        mvDB.addMovie("Sydney", "Die Hard" + randint, "synopsis", "M", newReleaseDate, "director", "cast", "Bronze");


    }

        @Test
        public void addMovieTestExists() {
    
            String resourcePath = "src/test/resources";
    
            MovieDatabase mvDB = new MovieDatabase(resourcePath);
            LocalDate newReleaseDate = LocalDate.parse("2000-11-25");
            mvDB.addMovie("Sydney", "The Matrix", "synopsis", "M", newReleaseDate, "director", "cast", "Bronze");
    
    
        }
    
        
        @Test
        public void movieToDetails() {
    
            String resourcePath = "src/test/resources";
    
            MovieDatabase mvDB = new MovieDatabase(resourcePath);
            LocalDate newReleaseDate = LocalDate.parse("2000-11-25");
            Movie m = new Movie("Sydney", "The Matrix", "synopsis", "M", newReleaseDate, "director", "cast", "Bronze");
            m.toDetails();
    
        }


}
