package movie_booking.Databases;

public class GiftCard {

    private String gcNumber;
    public boolean isUsed;
    private int amount;

    public GiftCard(String gcNumber, boolean isUsed, int amount) {
        this.gcNumber = gcNumber;
        this.isUsed = isUsed;
        this.amount = amount;
    }

    public String getGcNumber() {
        return gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public boolean setUsed(boolean used) {
        isUsed = used;
        return true;
    }

    public int getAmount() {
        return amount;
    }
}