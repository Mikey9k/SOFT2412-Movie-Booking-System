package movie_booking.Databases;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class CreditCardDatabase {

    private List<CreditCard> creditcards;

    public CreditCardDatabase(String resourcePath) {
        Gson gson = new Gson();
        String file = resourcePath + "/credit_cards.json";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Type type = new TypeToken<List<CreditCard>>(){}.getType();
            this.creditcards = gson.fromJson(br, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean verifyCCDetails(String cardholderName, String creditCardNumber) {

        for (CreditCard cc : creditcards) {
            String name = cc.getName();
            String number = cc.getNumber();

            if (cardholderName.equals(name) && creditCardNumber.equals(number)) {
                return true;
            }

        }

        return false;
    }
}
