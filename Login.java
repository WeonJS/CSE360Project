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
	
	public class LoginSession {
		private String hashedUser;
		private String hashedPass;
		public LoginSession(String user, String pass) {
			hashedUser = LoginDataHandler.hash(user);
			hashedPass = LoginDataHandler.hash(pass);
		}
		
		public String getHashedUser() {
			return hashedUser;
		}
		
		public String getHashedPass() {
			return hashedPass;
		}
	}
	
	private int passwordAttempts = 0;
	public static int MAX_ATTEMPTS = 5;
	private LoginSession loginSession = null;
	private LoginDataHandler dh = new LoginDataHandler();;
	
	public Login() {
	}
	
    public boolean handleLoginAttempt(String username, String password) {
    	//in practice will have a reset timer for login attempts
    	if(passwordAttempts >= MAX_ATTEMPTS) {
    		return false;
    	}

        // Check the login credentials with the UsernameDatabase
        boolean isValidLogin = dh.validateLogin(username, password);

        if (isValidLogin) {
            loginSession = new LoginSession(username, password);
            EffortLogger.getInstance().getEffortDataHandler().retrieveEfforts();
            return true;
        } else {
            passwordAttempts++;
        }
        return false;
    }
    
    public String attemptCreateAccount(String newUsername, String newPassword) {
  
        // Check if the new username already exists
        if (dh.existingUsername(newUsername) ) {
            return "Username exists. Login Enabled";
        }
        
        //check to see if username is valid input
        else if (!dh.isValidUsername(newUsername)) {
            return "Account Creation Failed\", \"Invalid Username. Please refer to username requirements";
        }
        //check to see if password is valid input
        else if (!dh.isValidPassword(newPassword)) {
        	return "Account Creation Failed\", \"Invalid Password. Please refer to password requirements";
        }
        else {
            // Add the new username and password to the database
        	dh.addUser(newUsername, newPassword);
        	return "Account created";
        }
    }
    
    public LoginSession getLoginSession() {
    	return loginSession;
    }
    
    public LoginDataHandler getLoginDataHandler() {
    	return dh;
    }
    
    public int getAttempts() {
    	return passwordAttempts;
    }
}
