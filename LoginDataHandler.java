package CSE360Project;

import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Nichoals Lorenzini

public class LoginDataHandler {
    // Store usernames and corresponding passwords
    private Pattern usernamePattern;
    private Pattern passwordPattern;
    private Path loginDataPath;
    
    // Prototype Database for show-case purposes
    public LoginDataHandler() {
    	// calculate path based on os
    	if(System.getProperty("os.name").equals("Mac OS X")) {
			loginDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "/logins/");
		}
		else 
			loginDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "\\logins\\");
    	
    	try {
			// if the login data path does not exist, make it
    		System.out.println(loginDataPath);
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
    
    public static String hash(String rawUser) {
    	
    	// hash username
    	String hashedString = "";
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        byte[] hashedBytes = md.digest(rawUser.getBytes(StandardCharsets.UTF_8));
	        hashedString = bytesToHex(hashedBytes);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
		
		return hashedString;
	}
	
	public static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b)); // Convert byte to 2-digit hexadecimal representation
        }
        return hexStringBuilder.toString();
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
    	Path loginFilePath = Paths.get(loginDataPath.toString(), hash(username));
		if (Files.exists(loginFilePath)) {
			return true;
		}
		return false;
    }
    
    public boolean addUser(String username, String password) {
    	String hashedUser = hash(username);
    	String hashedPass = hash(password);
    	try {
    		Path loginFilePath = Paths.get(loginDataPath.toString(), hashedUser);
    		System.out.println("WROTE " + username);
    		Files.write(loginFilePath, hashedPass.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    		return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean validateLogin(String username, String password) {
    	String hashedUsername = hash(username);
    	String hashedPassword = hash(password);
    	Path userLoginPath = Paths.get(loginDataPath.toString(), hashedUsername);
    	try {
    		String storedPassword;
    		if (Files.exists(userLoginPath)) {
    			List<String> loginFileLines = Files.readAllLines(userLoginPath);
        		if (loginFileLines.size() > 0) {
            		storedPassword = loginFileLines.get(0);
            		if (storedPassword.equals(hashedPassword)) {
            			return true;
            		}
            	}
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
}