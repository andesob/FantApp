package no.ntnu.FantApp2.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String token;

    public LoggedInUser(String userId,  String token) {
        this.userId = userId;
        this.token = "Bearer " + token;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getToken() {
        return token;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}