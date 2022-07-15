package movie_booking.Databases;

import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;

public class ConfigDatabase {
    public int timeoutSeconds;
    public Map<String, Map<String, Double>> ticketPrices;
    public long transactionID;
    public transient String resourcePath = null;
    public ConfigDatabase() {

    }

    public void save() {
        if (resourcePath == null) 
            return;
            
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter file = new FileWriter(resourcePath + "/config.json");
            file.write(gson.toJson(this));
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
