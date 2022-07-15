package movie_booking.Databases;

import movie_booking.VirtConsole;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginDatabase {

    List<User> users;

    String resourcePath;
    VirtConsole con;

    public LoginDatabase(String resourcePath,VirtConsole con) {
        this.resourcePath = resourcePath;
        this.con = con;
        this.load();

    }

    public void load() {
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(resourcePath + "/users.json"));
            User[] obj = gson.fromJson(reader, User[].class);
            this.users = new ArrayList<User>(Arrays.asList(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean save() {
        try {
            Gson gson = new Gson();
            FileWriter file = new FileWriter(resourcePath + "/users.json");
            file.write(gson.toJson(users));
            file.flush();
            file.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkUserExists(String username) {
        for (User u : this.users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean registerUser(String username, String password, String userType) {

        // check unique username
        if (this.checkUserExists(username)) {
            return false;
        }

        User createdUser = new User(username, password, userType);
        users.add(createdUser);

        return this.save();

    }

    public boolean verifyLogin(String username, String password) {
        for (User u : this.users) {
            if (u.getUsername().equals(username)) {
                if (u.getPassword().equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }




    /**
     * Allows user to register for an account
     * 
     * @return true to exit with successful registration, false if unsuccessful
     */
    public User registerUserInterface(String userType) {

        // Get username
        con.println("Please enter a username.\n"
        + "Or enter 'cancel' to cancel.");

        String userName = con.nextLine();

        if (userName.equals("cancel")) {
            con.println("Registration cancelled.\n");
            return null;
        }

        if (userName.equals("")) {
            con.println("Username blank, registration failed!\n");
            return null;
        }

        // Get password
        con.println("Please enter a password.\n"
        + "Or enter 'cancel' to cancel.");

        String password = con.nextLine();

        if (password.equals("cancel")) {
            con.println("Registration cancelled.\n");
            return null;
        }

        if (password.equals("")) {
            con.println("Password blank, registration failed!\n");
            return null;
        }

        if (this.registerUser(userName, password, userType)) {
            con.println("Registration successful! You are now logged in to your new account.");
            return getUser(userName);
            

        } else {
            con.println("Registration failed!");
            return null;
        }
    }

    public User getUser(String username) {
        for (User u : this.users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public List<User> getUserList() {
        return this.users;
    }
}
