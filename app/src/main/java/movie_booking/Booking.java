package movie_booking;

import movie_booking.Databases.*;
import java.util.Map;

/**
 * Stores information about a booking
 */
public class Booking {

    private Map<String, Integer> numTickets;
    private Map<String, Integer> seats;
    public double total;

    private MovieScreening screening;

    public Booking(MovieScreening screening, Map<String, Integer> tickets, double total) {
        this.screening = screening;
        this.numTickets = tickets;
        this.total = total;
    }

    /**
     * adds the map of tickets to this data structure/class
     * 
     * @param tickets
     * @return boolean - but probably will have to change this later
     */
    public boolean addNumTickets(Map<String, Integer> tickets) {
        this.numTickets = tickets;
        return true;
    }

    /**
     * adds the hashmap of the allocated seats
     * 
     * @param seats
     * @return boolean - but probably will have to change this later
     */
    public boolean addAllocatedSeats(Map<String, Integer> seats) {
        this.seats = seats;
        return true;
    }

    /**
     * retrieves the total number of tickets the user wants to purchase
     * 
     * @return the total number of tickets the user wants to purchase
     */
    public int getTotalTickets() {
        return this.numTickets.get("total");
    }

    public void finalise() {
        screening.setSeatsAvailableFront(screening.getSeatsAvailableFront() - seats.get("front"));
        screening.setSeatsAvailableMiddle(screening.getSeatsAvailableMiddle() - seats.get("middle"));
        screening.setSeatsAvailableBack(screening.getSeatsAvailableBack() - seats.get("rear"));
    }

}
