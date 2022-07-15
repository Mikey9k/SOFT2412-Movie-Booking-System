package movie_booking.Databases;

import java.time.LocalDateTime;

/**
 * Stores information about a booking
 */
public class Cancellation {

    private String reason;
    private LocalDateTime time;
    private User user ;

    public Cancellation(String reason, LocalDateTime time, User user) {
        this.reason = reason;
        this.time = time;
        this.user = user;
    }

    public String toCancelReportString() {
        if(this.user == null){
            this.user = new User("guest", "", "guestuser");
        }
        StringBuilder out = new StringBuilder();
        out.append("User: ");
        out.append(user.getUsername());
        out.append("\t");
        out.append("Time: ");
        out.append(time.toString());
        out.append("\t");
        out.append("Reason: ");
        out.append(reason);
        out.append("\n");
    return out.toString();
    }
}
