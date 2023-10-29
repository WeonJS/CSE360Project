package CSE360Project;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Nicholas Lorenzini 

public class Login {
	public TabPane tabPane = new TabPane();
	private String hashedUsername;
	private int passwordAttempts = 0;
	static int MAX_ATTEMPTS = 5;
	LoginDataHandler dh = new LoginDataHandler();
	
	public Login(String rawUser, String rawPassword) {
		// hash username
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(rawUser.getBytes(StandardCharsets.UTF_8));
            hashedUsername = bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
	}
	
    private void handleLoginAttempt(String username, String password) {
    	
    	//in practice will have a reset timer for login attempts
    	if(passwordAttempts >= MAX_ATTEMPTS) {
    		showAlert("Too many failed attemps", "Please try again later");
    		return;
    	}

        // Check the login credentials with the UsernameDatabase
        boolean isValidLogin = dh.confirmExistingUser(username, password);

        if (isValidLogin) {
            showAlert("Login Successful", "Welcome, " + username + "!");
        } else {
            passwordAttempts++;
            showAlert("Login Failed", "Invalid username or password. Attempt #" + passwordAttempts);
        }
    }
    
    public String getHashedUsername() {
		return hashedUsername;
	}
	
	public static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b)); // Convert byte to 2-digit hexadecimal representation
        }
        return hexStringBuilder.toString();
    }

    
    private void createAccount(String newUsername, String newPassword) {
        // Check if the new username already exists
        if (dh.existingUsername(newUsername)) {
            showAlert("Account Creation Failed", "Username already exists. Please login instead.");
        } 
        //check to see if username is valid input
        else if (!dh.isValidUsername(newUsername)) {
            showAlert("Account Creation Failed", "Invalid Username. Please refer to username requirements");
        }
        //check to see if password is valid input
        else if (!dh.isValidPassword(newPassword)) {
        	showAlert("Account Creation Failed", "Invalid Password. Please refer to password requirements");
        }
        else {
            // Add the new username and password to the database
        	dh.addUser(newUsername, newPassword);
            showAlert("Account Created", "Account for " + newUsername + " has been created.");
        }
    }
    
    //Alert creation function
    private void showAlert(String title, String content) {
    }
}
