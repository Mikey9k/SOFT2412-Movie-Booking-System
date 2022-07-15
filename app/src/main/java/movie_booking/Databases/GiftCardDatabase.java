package movie_booking.Databases;

import movie_booking.VirtConsole;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftCardDatabase {

    List<GiftCard> giftcards;
    String resourcePath;
    VirtConsole con;

    public GiftCardDatabase(String resourcePath,VirtConsole con) {
        this.resourcePath = resourcePath;
        this.con = con;
        this.load();

    }

    public void load() {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(resourcePath + "/gift_cards.json"));
            GiftCard[] obj = gson.fromJson(reader, GiftCard[].class);
            this.giftcards = new ArrayList<GiftCard>(Arrays.asList(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean save() {
        try {
            Gson gson = new Gson();
            FileWriter file = new FileWriter(resourcePath + "/gift_cards.json");
            file.write(gson.toJson(giftcards));
            file.flush();
            file.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkGCExits(String gcNumber) {
        for (GiftCard gc : this.giftcards) {
            if (gc.getGcNumber().equals(gcNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkGCUsed(String gcNumber) {
        for (GiftCard gc : this.giftcards) {
            if (gc.getGcNumber().equals(gcNumber)) {
                return gc.isUsed();
            }
        }
        return false;
    }

    public String addGiftCard(int amount) {

        long num = (long) (Math.random() * 10000000000000000L); // generates a random 16 digit integer for the gift
        // card number
        String gcNumber = num + "GC";

        // check unique username
        if (this.checkGCExits(gcNumber)) {
            return "-1";
        }

        GiftCard newGC = new GiftCard(gcNumber, false, amount);
        giftcards.add(newGC);

        if (this.save()) {
            return gcNumber;
        } else {
            return "-1";
        }

    }

    public boolean addGiftCardInterface() {

        con.println("\n-------------------------");
        con.println("Please enter how much you would like to load onto the gift card.\n" +
                "\tEnter '20' if you would like to add a $20 gift card.\n" +
                "\tEnter '50' if you would like to add a $50 gift card.\n" +
                "\tEnter '100' if you would like to add a $100 gift card.\n" +
                "\tEnter 'cancel' if you no longer want to add a gift card.\n");
        String gcAmount = con.nextLine();

        switch (gcAmount) {
            case "20":

            case "50":

            case "100":

                int integerAmount = 0;

                try {
                    integerAmount = Integer.parseInt(gcAmount);
                } catch (NumberFormatException e) {
                    con.println("You did not enter an integer. Please try again.\n");
                    this.addGiftCardInterface();
                }

                String gcNumber = this.addGiftCard(integerAmount);

                if (gcNumber == "-1") {
                    con.println("\nGift Card Not Added.\n" +
                            "Please try again.");
                    this.addGiftCardInterface();

                } else {
                    con.println("-------------------------");
                    con.println("\nGift card added!\n" +
                            "The gift card number is: " + gcNumber +
                            "\nThe gift card contains $" + integerAmount
                    );
                    return true;
                }

                break;

            case "cancel":
                break;

            default:
                con.println("-------------------------");
                con.println("\nInvalid option. Please try again.");
                this.addGiftCardInterface();
        }

        return false;
    }

    public int getGiftCardValue(String gcNumber) {
        for (GiftCard gc : this.giftcards) {
            if (gc.getGcNumber().equals(gcNumber)) {
                return gc.getAmount();
            }
        }
        return -1;
    }

    public boolean useGiftCard(String giftCardNumber) {

        for (GiftCard gc : this.giftcards) {
            if (gc.getGcNumber().equals(giftCardNumber)) {
                return gc.setUsed(true);
            }
        }

        this.save();

        return false;
    }

}
