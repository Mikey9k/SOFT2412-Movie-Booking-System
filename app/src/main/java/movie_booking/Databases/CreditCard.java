package movie_booking.Databases;


public class CreditCard {

    private String name;
    private String number;

    public CreditCard(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "name='" + name + '\'' +
                ", ccNumber='" + number + '\'' +
                '}';
    }
}
