package Accounts;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;

public abstract class Account {
    private String username;
    private String email;
    private boolean isAdmin;
    private String password;
    private String id;
    private String dateOfCreation;

    public Account(String aUsername, String aEmail, boolean adminStatus, String aPassword) {
        this.username = aUsername;
        this.email = aEmail;
        this.isAdmin = adminStatus;
        this.password = aPassword;
        
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);
        this.dateOfCreation = formattedToday;

        // Possible feature: proper check of every input as well as e-mail check to decide if that new user is allowed to
        // sign up for that type of Account
    }

    public void setId(String newID) {
        id = newID;
    }

    /**
     * This method returns a boolean
     * @return true if Account is Admin
     *         false if Account is User
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getDateOFCreation() {
        return dateOfCreation;
    }
    public String toString() {
        return new Gson().toJson(this);
    }
}
