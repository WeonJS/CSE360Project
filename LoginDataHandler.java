package CSE360Project;

import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Nichoals Lorenzini

public class LoginDataHandler {
    // Store usernames and corresponding passwords
    private Map<String, String> users;
    private Pattern usernamePattern;
    private Pattern passwordPattern;
    private Path loginDataPath;
    
    // Prototype Database for show-case purposes
    public LoginDataHandler() {
    	// calculate path based on os
    	if(System.getProperty("os.name").equals("Mac OS X")) {
			loginDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "/Documents/EffortLogger/logins/");
		}
		else 
			loginDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "\\EffortLogger\\logins\\");
    	
    	try {
			// if the login data path does not exist, make it
			if (Files.notExists(loginDataPath)) {
				Files.createDirectories(loginDataPath);
			}
			
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
        // username requirements: 
        // between 8 and 16 characters long; 
        // Alphanumeric, numbers, "-", "_" allowed
        usernamePattern = Pattern.compile("^[a-zA-Z0-9-_]{8,16}$");
        
        // password requirements
        // between 8 and 16 characters;
        // (at least 1) Alphanumeric;
        // (at least 1) number;
        // (at least 1) capital letter(s) and special character(s)
        // most special characters allowed
        passwordPattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#\\$%^&*()_+\\-=\\[\\]\\{}|;:'\",.<>/?]).{8,16}$");
    }
    
    // method to check login information exists and is correctly matched
    public boolean confirmExistingUser(String username, String password) {
        
    	
    	//return users.get(username).equals(password);
        return false;
    }
    
    //method to detect validity of username for creating an account
    public boolean isValidUsername(String username) {
    	return usernamePattern.matcher(username).matches();
    }

    // method to detect validity of password for creating an account
    public boolean isValidPassword(String password) {
    	return passwordPattern.matcher(password).matches();
    }
    
    public boolean existingUsername(String username) {
        return users.containsKey(username);
    }

    // method to add user, checks implemented in LoginHandler class
    public void addUser(String username, String password) {
        users.put(username, password);
    }
    
}