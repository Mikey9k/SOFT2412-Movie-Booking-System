package movie_booking.Databases;

public class User {

    private String username;
    private String password;
    private String userType;
    private String savedCardName;
    private String savedCardNumber;
    private boolean ccDetails = false;

    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(String username, String password, String userType, String savedCardName, String savedCardNumber,
            boolean ccDetails) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.savedCardName = savedCardName;
        this.savedCardNumber = savedCardNumber;
        this.ccDetails = ccDetails;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        switch (type) {
            case "S":
                this.userType = "staff";
                break;
            case "M":
                this.userType = "manager";
                break;
            case "C":
                this.userType = "customer";
                break;
        }

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void containsCCDetails(Boolean ccDetails) {
        this.ccDetails = ccDetails;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserType() {
        return this.userType;
    }

    public void saveCCDetails(String cardName, String cardNumber) {
        this.savedCardName = cardName;
        this.savedCardNumber = cardNumber;
    }

    public boolean hasCCDetails() {
        return this.ccDetails;
    }

    public String getCardNumber() {
        return this.savedCardNumber;
    }

    public String getCardName() {
        return this.savedCardName;
    }


    public String toDetails() {
        StringBuilder out = new StringBuilder();
        out.append("Username: ");
        out.append(username);
        out.append("\nType: ");
        out.append(userType);
        out.append("\nPayment saved: ");
        out.append(ccDetails);
        return out.toString();
    }
}
