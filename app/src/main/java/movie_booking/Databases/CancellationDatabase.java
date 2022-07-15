package movie_booking.Databases;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import movie_booking.VirtConsole;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class CancellationDatabase {

    private List<Cancellation> cancellations = new ArrayList<Cancellation>();

    private String resourcePath;

    public CancellationDatabase(String resourcePath) {
        this.resourcePath = resourcePath;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(resourcePath + "/cancellations.json"));
            Cancellation[] obj = gson.fromJson(reader, Cancellation[].class);
            this.cancellations = new ArrayList<Cancellation>(Arrays.asList(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.save();
    }

    public void addCancellation(Cancellation cancellation){
        cancellations.add(cancellation);
        this.save();
    }

    public List<Cancellation> getCancellations() {
        return cancellations;
    }

    public void save() {
        Gson gson = new Gson();
        try {
            FileWriter file = new FileWriter(resourcePath + "/cancellations.json");
            file.write(gson.toJson(cancellations));
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void cancelReport(VirtConsole con) {

        try{
            
        File CancelReport = new File("CancelReport.txt");
        FileWriter writer = new FileWriter(CancelReport);


        writer.write("`-._,-'`-._,-'`-._,-'`-._,-\n'");
        writer.write("  HOYTSOFT CANCELLATION REPORT \n");
        writer.write("`-._,-'`-._,-'`-._,-'`-._,-\n'");
        writer.write("-------------------------\n");

        if(!cancellations.isEmpty()){
                
        

        List<String> cancelPage = cancellations.stream().map(Cancellation::toCancelReportString).collect(Collectors.toList());

        for (int i = 0; i < cancelPage.size(); i++) {
            writer.write("-------------------------\n");
            writer.write(cancelPage.get(i));
        }
    }
        writer.write("-------------------------");
        writer.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    }
}