package movie_booking.Databases;

import java.time.*;
import java.time.format.DateTimeFormatter;


public class MovieScreening {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime screeningTime;

    int bookingCount;

    int seatsAvailableFront;

    

    int seatsAvailableMiddle;
    int seatsAvailableBack;

    int seatsBookedFront;
    int seatsBookedMiddle;
    int seatsBookedBack;

    public MovieScreening(LocalTime screeningTime, int bookingCount, int seatsAvailableFront, int seatsAvailableMiddle, int seatsAvailableBack, int seatsBookedFront, int seatsBookedMiddle, int seatsBookedBack) {
        this.screeningTime = screeningTime;
        this.bookingCount = bookingCount;
        this.seatsAvailableFront = seatsAvailableFront;
        this.seatsAvailableMiddle = seatsAvailableMiddle;
        this.seatsAvailableBack = seatsAvailableBack;
        this.seatsBookedFront = seatsBookedFront;
        this.seatsBookedMiddle = seatsBookedMiddle;
        this.seatsBookedBack = seatsBookedBack;
    }

    public int getBookedSeats() {
        return seatsBookedFront + seatsBookedMiddle + seatsBookedBack;
    }

    public int getAvailableSeats() {
        return seatsAvailableFront + seatsAvailableMiddle + seatsAvailableBack;
    }

    public LocalTime getScreeningTime() {
        return screeningTime;
    }

    public String getTimeDisplay() {
        return screeningTime.format(formatter);
    }

    public int getSeatsAvailableFront() {
        return seatsAvailableFront;
    }

    public int getSeatsAvailableMiddle() {
        return seatsAvailableMiddle;
    }

    public int getSeatsAvailableBack() {
        return seatsAvailableBack;
    }

    public void setSeatsAvailableFront(int seatsAvailableFront) {
        this.seatsAvailableFront = seatsAvailableFront;
    }

    public void setSeatsAvailableMiddle(int seatsAvailableMiddle) {
        this.seatsAvailableMiddle = seatsAvailableMiddle;
    }

    public void setSeatsAvailableBack(int seatsAvailableBack) {
        this.seatsAvailableBack = seatsAvailableBack;
    }
}
