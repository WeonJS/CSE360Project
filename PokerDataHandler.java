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

import java.util.List;


// Nichoals Lorenzini

public class PokerDataHandler {
    // Store usernames and corresponding passwords
    private Pattern IDPattern;
    private Pattern passwordPattern;
    private Path PokerDataPath;
    
    // Prototype Database for show-case purposes
    public PokerDataHandler() {
    	// calculate path based on OS
    	if(System.getProperty("os.name").equals("Mac OS X")) {
			PokerDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "/sessions/");
		}
		else 
			PokerDataPath = Paths.get(EffortLogger.getInstance().getRootDirectory() + "\\sessions\\");
    	
    	// create logins folder if it doesnt exist
    	FileDirectory.createFolder(PokerDataPath);
    	
        // username requirements: 
        // between 8 and 16 characters long; 
        // Alphanumeric, numbers, "-", "_" allowed
        IDPattern = Pattern.compile("^[a-zA-Z0-9-_]{8,16}$");
        
        // username requirements: 
        // between 8 and 16 characters long; 
        // Alphanumeric, numbers, "-", "_" allowed
        passwordPattern = Pattern.compile("^[a-zA-Z0-9-_]{8,16}$");
    }
    
public static String hash(String rawID) { //i NEED THE HASHED USERNAME ohion got the user 
    	
    	// hash username
    	String hashedString = "";
		try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        byte[] hashedBytes = md.digest(rawID.getBytes(StandardCharsets.UTF_8));
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
    public boolean isValidID(String ID) {
    	return IDPattern.matcher(ID).matches();
    }

    // method to detect validity of password for creating an account
    public boolean isValidPassword(String password) {
    	return passwordPattern.matcher(password).matches();
    }
    
    public boolean existingUsername(String ID) {
    	Path PokerFilePath = Paths.get(PokerDataPath.toString(), hash(ID));
		return FileDirectory.fileExists(PokerFilePath);
    }
    
    //called when creating new acc...
    public boolean addSession(String ID, String password) {
    	String hashedID = hash(ID);
    	String hashedPass = hash(password);
    	Path PokerFilePath = Paths.get(PokerDataPath.toString(), hashedID);
		
		return FileDirectory.writeToFile(PokerFilePath, hashedPass);
    }
    
    //function called on login... validates information
    public boolean validateLogin(String ID, String password) {
    	String hashedID = hash(ID);
    	String hashedPassword = hash(password);
    	Path userPokerPath = Paths.get(PokerDataPath.toString(), hashedID);
    	
    	// match the login details to the file
    	if (FileDirectory.fileExists(userPokerPath)) {
    		String storedPassword;
    		List<String> pokerFileLines = FileDirectory.getFileLines(userPokerPath);
    		if (pokerFileLines.size() > 0) {
        		storedPassword = pokerFileLines.get(0);
        		return storedPassword.equals(hashedPassword);
        	}
    	}
    	return false;
    }
    
}