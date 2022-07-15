package movie_booking.Databases;

import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Movie {
    String cinema;
    String name;
    String synopsis;
    String classification;
    LocalDate releaseDate;
    String director;
    String cast;
    String screenSize;

    public Map<String, List<MovieScreening>> showTimes;

    public static final String[] days = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday" };

    public Movie() {
        this.showTimes = new HashMap<String, List<MovieScreening>>();
        for (var d : days) {
            if (!showTimes.containsKey(d))
                showTimes.put(d, new ArrayList<MovieScreening>());
        }
    }

    public Movie(String cinema, String name, String synopsis, String classification, LocalDate releaseDate,
            String director, String cast, String screenSize) {
        this.cinema = cinema;
        this.name = name;
        this.synopsis = synopsis;
        this.classification = classification;
        this.releaseDate = releaseDate;
        this.director = director;
        this.cast = cast;
        this.screenSize = screenSize;
        this.showTimes = new HashMap<String, List<MovieScreening>>();
        for (var d : days) {
            if (!showTimes.containsKey(d))
                showTimes.put(d, new ArrayList<MovieScreening>());
        }
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }


    public String getName() {
        return name;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public String getCinema() {
        return cinema;
    }

    public Map<String, List<MovieScreening>> getShowTimes() {
        return showTimes;
    }

    public boolean showsInWeekAfter(LocalDateTime start) {
        int day = start.getDayOfWeek().getValue() - 1;
        return Stream.of(days).skip(day + 1).flatMap(d -> showTimes.get(d).stream()).anyMatch(s -> true)
                || showTimes.get(days[day]).stream().anyMatch(s -> s.screeningTime.isAfter(start.toLocalTime()));
    }

    public List<MovieScreening> showsAfterTimeOnDay(LocalDate date, LocalTime time) {
        int day = date.getDayOfWeek().getValue() - 1;
        return showTimes.get(days[day]).stream().filter(s -> s.screeningTime.isAfter(time))
                .collect(Collectors.toList());
    }

    public String toShortDisplay() {
        StringBuilder out = new StringBuilder();
        out.append(name);
        out.append("\t(");
        out.append(classification);
        out.append(")\n");
        out.append("Showing at: ");
        out.append(cinema);
        out.append("\nScreen size: ");
        out.append(screenSize);
        out.append("\nShowing times:\n");
        for (int d = 0; d < 7; d++) {
            if (showTimes.get(days[d]).size() > 0) {
                out.append("  ");
                out.append(days[d]);
                out.append(":\n");
                for (var dt : showTimes.get(days[d])) {
                    out.append("\t");
                    out.append(dt.getTimeDisplay());
                }
                out.append("\n");
            }
        }
        out.append("\n");
        return out.toString();
    }




    public String toShortDisplay(LocalDate date, LocalTime after) {

        StringBuilder out = new StringBuilder();
        out.append(name);
        out.append("\t(");
        out.append(classification);
        out.append(")\n");
        out.append("Showing at: ");
        out.append(cinema);
        out.append("\nScreen size: ");
        out.append(screenSize);
        out.append("\nShowing times:\n");
        int day = date.getDayOfWeek().getValue() - 1;
        List<MovieScreening> showsToday = showsAfterTimeOnDay(date, after);
        if (showsToday.size() > 0) {
            out.append("  ");
            out.append(days[day]);
            out.append(":\n");
            for (var dt : showsToday) {
                out.append("\t");
                out.append(dt.getTimeDisplay());
            }
            out.append("\n");
        }

        for (int d = day + 1; d < 7; d++) {
            if (showTimes.get(days[d]).size() > 0) {
                out.append("  ");
                out.append(days[d]);
                out.append(":\n");
                for (var dt : showTimes.get(days[d])) {
                    out.append("\t");
                    out.append(dt.getTimeDisplay());
                }
                out.append("\n");
            }
        }
        out.append("\n");
        return out.toString();
    }

    public String toDetails() {
        StringBuilder out = new StringBuilder();
        out.append(name);
        out.append("\n\nSynopsis: ");
        out.append(synopsis);
        out.append("\nClassification: ");
        out.append(classification);
        out.append("\nRelease Date: ");
        out.append(releaseDate.toString());
        out.append("\nDirector: ");
        out.append(director);
        out.append("\nCast: ");
        out.append(cast);
        out.append("\nScreen Size: ");
        out.append(screenSize);
        out.append("\nShowing times:\n");
        for (int d = 0; d < 7; d++) {
            if (showTimes.get(days[d]).size() > 0) {
                out.append("  ");
                out.append(days[d]);
                out.append(":\n");
                for (var dt : showTimes.get(days[d])) {
                    out.append("\t");
                    out.append(dt.getTimeDisplay());
                }
                out.append("\n");
            }
        }

        return out.toString();
    }

    public String toDetails(LocalDateTime after) {
        StringBuilder out = new StringBuilder();
        out.append(name);
        out.append("\n\nSynopsis: ");
        out.append(synopsis);
        out.append("\nClassification: ");
        out.append(classification);
        out.append("\nRelease Date: ");
        out.append(releaseDate.toString());
        out.append("\nDirector: ");
        out.append(director);
        out.append("\nCast: ");
        out.append(cast);
        out.append("\nScreen Size: ");
        out.append(screenSize);
        out.append("\nShowing times:\n");

        LocalDate date = after.toLocalDate();
        LocalTime time = after.toLocalTime();
        int day = date.getDayOfWeek().getValue() - 1;
        List<MovieScreening> showsToday = showsAfterTimeOnDay(date, time);
        if (showsToday.size() > 0) {
            out.append("  ");
            out.append(days[day]);
            out.append(":\n");
            for (var dt : showsToday) {
                out.append("\t");
                out.append(dt.getTimeDisplay());
            }
            out.append("\n");
        }

        for (int d = day + 1; d < 7; d++) {
            if (showTimes.get(days[d]).size() > 0) {
                out.append("  ");
                out.append(days[d]);
                out.append(":\n");
                for (var dt : showTimes.get(days[d])) {
                    out.append("\t");
                    out.append(dt.getTimeDisplay());
                }
                out.append("\n");
            }
        }

        return out.toString();
    }

    private static boolean checkTimeEqual(LocalTime showTime, LocalTime userEntry) {
        return showTime.withNano(0).withSecond(0).equals(userEntry.withNano(0).withSecond(0));
    }

    public boolean checkShowAvailable(String dayOfWeek, LocalTime showTime) {
        int day = LocalDate.now().getDayOfWeek().getValue() - 1;
        if (dayOfWeek.equals(days[day])) {
            return showsAfterTimeOnDay(LocalDate.now(), LocalTime.now()).stream().anyMatch(s -> checkTimeEqual(s.screeningTime, showTime));
        }
        else {
            return showTimes.get(dayOfWeek).stream().anyMatch(s -> checkTimeEqual(s.screeningTime, showTime));
        }
    }

    public MovieScreening getMovieScreening(String dayOfWeek, LocalTime showTime) {
        int day = LocalDate.now().getDayOfWeek().getValue() - 1;
        if (dayOfWeek.equals(days[day])) {
            return showsAfterTimeOnDay(LocalDate.now(), LocalTime.now()).stream().filter(s -> checkTimeEqual(s.screeningTime, showTime)).findAny().get();
        }
        else {
            return showTimes.get(dayOfWeek).stream().filter(s -> checkTimeEqual(s.screeningTime, showTime)).findAny().get();
        }
    }

    
    public String toBookingReportString() {
        StringBuilder out = new StringBuilder();
        out.append(name);
        out.append("\t(");
        out.append(classification);
        out.append(")\n");
        out.append("Showing at: ");
        out.append(cinema);
        out.append("\nScreen size: ");
        out.append(screenSize);
        out.append("\nShowing times:\n\n");
        for (int d = 0; d < 7; d++) {
            if (showTimes.get(days[d]).size() > 0) {
                out.append("  ");
                out.append(days[d]);
                out.append(":\n\n");
                for (var dt : showTimes.get(days[d])) {
                    out.append("\t");
                    out.append(dt.getTimeDisplay());
                    out.append("\n");
                    out.append("Total " + Integer.toString(dt.getBookedSeats() + dt.getAvailableSeats()) + " seats.");
                    out.append("\n");
                    out.append(Integer.toString(dt.getBookedSeats()) + " Seats currently booked.");
                    out.append("\n");
                    out.append(Integer.toString(dt.getAvailableSeats()) + " Seats left available.\n\n");

                }
                out.append("\n");
            }
        }
        out.append("\n");
        return out.toString();
    }

    public String toMovieReportString() {
        StringBuilder out = new StringBuilder();
        out.append(name);
        out.append("\t(");
        out.append(classification);
        out.append(")\n");
        out.append("\n*********************");
        out.append("\n*** Movie Details ***");
        out.append("\n*********************");
        out.append("\nSynopsis: ");
        out.append(synopsis);
        out.append("\nDirector: ");
        out.append(director);
        out.append("\nCast: ");
        out.append(cast);
        out.append("\n\n********************");
        out.append("\n*** Show Details ***");
        out.append("\n********************");
        out.append("\nRelease Date: ");
        out.append(releaseDate);
        out.append("\nShowing at: ");
        out.append(cinema);
        out.append("\nScreen size: ");
        out.append(screenSize);
        out.append("\nShowing times:\n\n");
        for (int d = 0; d < 7; d++) {
            if (showTimes.get(days[d]).size() > 0) {
                out.append("  ");
                out.append(days[d]);
                out.append(":\n\n");
                for (var dt : showTimes.get(days[d])) {
                    out.append("\t");
                    out.append(dt.getTimeDisplay());
                    out.append("\n");
                }
                out.append("\n");
            }
        }
        out.append("\n");
        return out.toString();
    }
}
