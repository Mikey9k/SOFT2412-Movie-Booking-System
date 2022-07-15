package movie_booking.Databases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import movie_booking.VirtConsole;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class MovieDatabase {
    public static final int MOVIES_PER_PAGE = 3;
    private Map<String, List<Movie>> moviesByCinema;

    private List<Movie> movieView;
    private int currentPage;
    private Movie selectedMovie;

    private LocalDateTime filterStartTime;
    private String filterCinema;
    private String filterScreenSize;

    private String resourcePath;

    public MovieDatabase(String resourcePath) {
        this.resourcePath = resourcePath;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(resourcePath + "/movies.json"));
            Type t = new TypeToken<Map<String, List<Movie>>>() {
            }.getType();
            moviesByCinema = gson.fromJson(reader, t);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.save();
        this.reset();
    }

    public boolean cinemaExists(String cinema) {
        return moviesByCinema.containsKey(cinema);
    }

    public Set<String> getCinemas() {
        return moviesByCinema.keySet();
    }

    public void save() {
        Gson gson = new Gson();
        try {
            FileWriter file = new FileWriter(resourcePath + "/movies.json");
            file.write(gson.toJson(moviesByCinema));
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void reset() {
        this.movieView = getAllMovies();

        // Reset to default time filter
        this.filterStartTime = LocalDateTime.now();

        // reset filter
        this.filterCinema = null;
        this.filterScreenSize = null;

        this.selectedMovie = null;

        // Reset page
        this.currentPage = 1;

        applyFilters();
    }

    public int getPageCount() {
        return (int) Math.ceil(movieView.size() / (float) MovieDatabase.MOVIES_PER_PAGE);
    }

    public void setPage(int page) {
        this.currentPage = page;
    }

    public void displayPage(VirtConsole con) {
        List<Movie> moviePage = movieView.stream().skip((currentPage - 1) * MOVIES_PER_PAGE).limit(MOVIES_PER_PAGE)
                .collect(Collectors.toList());

        con.println("");
        for (int i = 0; i < moviePage.size(); i++) {
            con.println("-------------------------");
            if (filterStartTime != null) {
                con.print(moviePage.get(i).toShortDisplay(filterStartTime.toLocalDate(), filterStartTime.toLocalTime()));
            }
            else {
                con.print(moviePage.get(i).toShortDisplay());
            }
            con.println(String.format("        {Movie option #%d}", i + 1));
        }
        con.println("-------------------------");
        con.format("Currently displaying page %d of %d.\n", currentPage, getPageCount());
        con.println("-------------------------");
    }

    public void displayMovieDetails(VirtConsole con) {
        con.println("-------------------------");
        if (filterStartTime != null) {
            con.println(selectedMovie.toDetails(filterStartTime));
        }
        else {
            con.println(selectedMovie.toDetails());
        }
        con.println("-------------------------");
    }

    public Set<String> displayScreenSizes() {
        Set<String> screenSizes = new HashSet<>();
        for (Movie m : getAllMovies()) {
            screenSizes.add(m.getScreenSize());
        }
        return screenSizes;
    }

    public void setScreenSizeFilter(String screenSize) {
        this.filterScreenSize = screenSize;
        applyFilters();
    }

    public void setCinemaFilter(String cinema) {
        this.filterCinema = cinema;
        applyFilters();
    }

    public void setTimeFilter(LocalDateTime start) {
        this.filterStartTime = start;
        applyFilters();
    }

    public boolean selectMovie(int option) {
        int idx = (this.currentPage - 1) * MOVIES_PER_PAGE + option - 1;
        if (option <= 0 || option > MOVIES_PER_PAGE || idx < 0 || idx >= movieView.size()) {
            return false;
        }

        this.selectedMovie = movieView.get(idx);
        return true;
    }

    public Movie getSelectedMovie() {
        return this.selectedMovie;
    }

    private void applyFilters() {
        if (filterCinema == null) {
            movieView = getAllMovies();
        } else {
            movieView = getMoviesInCinema(filterCinema);
        }

        if (filterScreenSize != null) {
            movieView = filterByScreenSize(movieView, filterScreenSize);
        }

        if (filterStartTime != null) {
            movieView = filterAvailable(movieView, filterStartTime);
        }

        int maxPage = getPageCount();
        if (currentPage > maxPage) {
            currentPage = maxPage > 0 ? maxPage : 1;
        }
    }

    public void displayFilters(VirtConsole con) {
        if (filterCinema == null)
            con.println("Currently displaying films from all cinemas.");
        else
            con.format("Currently displaying films showing at [%s].\n", filterCinema);

        if (filterStartTime == null)
            con.format("Time filter disabled.\n");
        else
            con.format("Viewing week starting %s.\n", filterStartTime.toLocalDate().toString());
        
        if (filterScreenSize == null)
            con.println("Currently displaying all screen sizes.");
        else
            con.format("Currently displaying only [%s] screens.\n", filterScreenSize);
    }

    private List<Movie> getAllMovies() {
        return moviesByCinema.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<Movie> getMoviesInCinema(String cinema) {
        if (!moviesByCinema.containsKey(cinema)) {
            return new ArrayList<Movie>();
        }
        return moviesByCinema.get(cinema);
    }

    private List<Movie> filterByScreenSize(List<Movie> movieView, String screenSize) {
        return movieView.stream().filter(m -> m.screenSize.equals(screenSize)).collect(Collectors.toList());
    }

    private List<Movie> filterAvailable(List<Movie> movieView, LocalDateTime start) {
        return movieView.stream().filter(m -> m.showsInWeekAfter(start)).collect(Collectors.toList());
    }


    public boolean addMovie(String cinema, String name, String synopsis, String classification,
                  LocalDate releaseDate, String director, String cast, String screenSize) {

        try {
            Movie createdMovie = new Movie(cinema, name, synopsis, classification, releaseDate,
                    director, cast, screenSize);
            
            if (!moviesByCinema.containsKey(cinema)) {
                moviesByCinema.put(cinema, new ArrayList<Movie>());
            }
            moviesByCinema.get(cinema).add(createdMovie);

            this.save();
            this.reset();
            this.setTimeFilter(null);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public boolean addShow(VirtConsole con) {

        try {
            con.println("");
            con.println("Current movie selected:");
            con.println(selectedMovie.getName());
            con.println("Which day of the week? ");
            String dayOfWeek = con.nextLine();

            if (!Arrays.stream(Movie.days).anyMatch(dayOfWeek::equals)) {
                con.println("Day of Week invalid, Show was not added");
                return false;
            }

            con.println("Please enter Screening time (HH:MM): ");
            String screeningTime = con.nextLine();

            LocalTime temp;
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime releaseDate = LocalTime.parse(screeningTime, formatter);

                temp = releaseDate;
            } catch (DateTimeParseException e) {
                con.println("Screening time invalid, Show was not added");
                return false;
            }

            MovieScreening createdScreening = new MovieScreening(temp, 0, 100, 100, 100, 0, 0, 0);
            selectedMovie.getShowTimes().get(dayOfWeek).add(createdScreening);

            this.save();
            this.reset();
            this.setTimeFilter(null);
            con.println("You have succesfully saved your changes.");
            con.println("Menu is now reset.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteShow(VirtConsole con) {

        try {
            con.println("");
            con.println("Current movie selected:");
            con.println(selectedMovie.getName());
            con.println("Delete from which day of the week? ");
            String dayOfWeek = con.nextLine();

            if (!Arrays.stream(Movie.days).anyMatch(dayOfWeek::equals)) {
                con.println("Day of Week invalid, Show was not added");
                return false;
            }

            con.println("Delete which Screening time? (HH:MM): ");
            String screeningTime = con.nextLine();

            LocalTime temp;
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime releaseDate = LocalTime.parse(screeningTime, formatter);

                temp = releaseDate;
            } catch (DateTimeParseException e) {
                con.println("Screening time invalid, Show was not added");
                return false;
            }

            List<MovieScreening> showsOnDay = selectedMovie.getShowTimes().get(dayOfWeek);


            for (MovieScreening ms: showsOnDay) {
                LocalTime upper;
                LocalTime lower;
                upper = temp.plusMinutes(1);
                lower = temp.plusMinutes(-1);

                if (ms.getScreeningTime().isAfter(lower) && ms.getScreeningTime().isBefore(upper)) {
                    showsOnDay.remove(ms);
                    this.save();
                    this.reset();
                    this.setTimeFilter(null);
                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void editMovieDetails(VirtConsole con) {

        boolean running = true;
        promptLoop: while (running) {
            con.println("");
            con.println("Current movie selected:");
            con.println(selectedMovie.getName());
            con.println("What would you like to edit?");
            con.println("------------------------------");
            con.println("Enter '1' to edit cinema location");
            con.println("Enter '2' to edit movie name");
            con.println("Enter '3' to edit synopsis");
            con.println("Enter '4' to edit classification");
            con.println("Enter '5' to edit release date");
            con.println("Enter '6' to edit director");
            con.println("Enter '7' to edit cast");
            con.println("Enter '8' to edit screensize");
            con.println("Enter 'q' or 'exit' to exit");
            con.println("------------------------------");
            String selection = con.nextLine();

            switch (selection) {

                case "1":
                    con.println("Please enter new Cinema Location: ");
                    String newLocation = con.nextLine();
                    selectedMovie.setCinema(newLocation);
                    break;

                case "2":
                    con.println("Please enter new Name: ");
                    String newName = con.nextLine();
                    selectedMovie.setName(newName);
                    break;

                case "3":
                    con.println("Please enter new Synopsis: ");
                    String newSynopsis = con.nextLine();
                    selectedMovie.setSynopsis(newSynopsis);
                    break;

                case "4":
                    con.println("Please enter new Classification: ");
                    String newClassification = con.nextLine();
                    selectedMovie.setClassification(newClassification);
                    break;

                case "5":
                    con.println("Please enter new Release Date (DD-MM-YYYY): ");
                    String stringRD = con.nextLine();
                    LocalDate newReleaseDate = LocalDate.parse(stringRD);
                    selectedMovie.setReleaseDate(newReleaseDate);
                    break;

                case "6":
                    con.println("Please enter new Director: ");
                    String newDirector = con.nextLine();
                    selectedMovie.setDirector(newDirector);
                    break;

                case "7":
                    con.println("Please enter new Cast: ");
                    String newCast = con.nextLine();
                    selectedMovie.setCast(newCast);
                    break;

                case "8":
                    con.println("Please enter new Screen Size: ");
                    String newScreenSize = con.nextLine();
                    selectedMovie.setScreenSize(newScreenSize);
                    break;

                case "q":
                case "exit":
                    running = false;
                    break;

                default:
                    con.println("Invalid option. Please try again.");
                    continue promptLoop;

            }
        }
        this.save();
        this.reset();
        this.setTimeFilter(null);
        con.println("You have succesfully saved your changes.");
        con.println("Menu is now reset.");
    }

    public boolean deleteMovie() {

        String location = selectedMovie.getCinema();
        List<Movie> moviesHere = moviesByCinema.get(location);

        for (Movie m: moviesHere) {
            if (selectedMovie.getName().equals(m.getName())) {
                moviesHere.remove(m);
                this.save();
                this.reset();
                this.setTimeFilter(null);

                return true;
            }
        }
        return false;
    }

    public void bookingsReport(VirtConsole con) {

        try{
        File bookingReport = new File("BookingReport.txt");
        FileWriter writer = new FileWriter(bookingReport);

        List<String> moviePage = movieView.stream().map(Movie::toBookingReportString).collect(Collectors.toList());

        writer.write("`-._,-'`-._,-'`-._,-'`-._,-\n'");
        writer.write("  HOYTSOFT BOOKINGS REPORT \n");
        writer.write("`-._,-'`-._,-'`-._,-'`-._,-\n'");
        writer.write("-------------------------\n");
        for (int i = 0; i < moviePage.size(); i++) {
            writer.write("-------------------------\n");
            writer.write(moviePage.get(i));
        }
        writer.write("-------------------------");
        writer.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    }

    public void moviesReport(VirtConsole con) {

        try{
            File movieReport = new File("MoviesReport.txt");
            FileWriter writer = new FileWriter(movieReport);

            List<String> moviePage =
                    movieView.stream().map(Movie::toMovieReportString).collect(Collectors.toList());

            writer.write("`-._,-'`-._,-'`-._,-'`-._,-\n'");
            writer.write("  HOYTSOFT MOVIES REPORT \n");
            writer.write("`-._,-'`-._,-'`-._,-'`-._,-\n'");
            writer.write("-------------------------\n");
            writer.write(" _ __ ___   _____   _(_) ___  ___ \n");
            writer.write("| '_ ` _ \\ / _ \\ \\ / / |/ _ \\/ __|\n");
            writer.write("| | | | | | (_) \\ V /| |  __/\\__ \\\n");
            writer.write("|_| |_| |_|\\___/ \\_/ |_|\\___||___/\n");

            for (int i = 0; i < moviePage.size(); i++) {
                writer.write("-------------------------\n");
                writer.write(moviePage.get(i));
            }
            writer.write("-------------------------");
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}