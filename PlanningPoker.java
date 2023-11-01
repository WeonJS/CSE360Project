package CSE360Project;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import CSE360Project.Login.LoginSession;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Nicholas Lorenzini 

public class PlanningPoker {
	
	public class PokerSession {
		private String hashedUser;
		private String hashedPass;
		public PokerSession(String ID, String pass) {
			hashedUser = PokerDataHandler.hash(ID);
			hashedPass = PokerDataHandler.hash(pass);
		}
		
		public String getHashedUser() {
			return hashedUser;
		}
		
		public String getHashedPass() {
			return hashedPass;
		}
	}
	
	
	
	private PokerSession pokerSession = null;
	private PokerDataHandler dh = new PokerDataHandler();
	private ArrayList<String> newTopics = new ArrayList<>();
	
	public PlanningPoker() {
	}
	
	 public boolean handleJoinAttempt(String ID, String password) {
	        // Check the login credentials with the UsernameDatabase
	        boolean isValidLogin = dh.validateLogin(ID, password);

	        if (isValidLogin) {
	            pokerSession = new PokerSession(ID, password);
	            return true;
	        } else {
	          return false;
	        }
	        
	    }
	    
	    public String attemptCreateSession(String newID, String newPassword, String topics) {
	  
	        // Check if the new username already exists
	        if (dh.existingUsername(newID) ) {
	            return "ID exists. Please enter a new ID";
	        }
	        
	        //check to see if username is valid input
	        else if (!dh.isValidID(newID)) {
	            return "Session Creation Failed\", \"Invalid ID. Please refer to username requirements";
	        }
	        //check to see if password is valid input
	        else if (!dh.isValidPassword(newPassword)) {
	        	return "Session Creation Failed\", \"Invalid Password. Please refer to password requirements";
	        }
	        else {
	            // Add the new ID, Password and populate Topics List
	        	newTopics.clear();
	        	String[] parts = topics.split(",");
	        	for (String part : parts) {
	                newTopics.add(part.trim()); // Use trim to remove leading/trailing spaces
	            }
	        	dh.addSession(newID, newPassword);
	        	return "Session created";
	        }
	    }
	    
	    public ArrayList<String> getTopics() {
	    	return newTopics;
	    }
	    
	    public PokerSession getPokerSession() {
	    	return pokerSession;
	    }
	    
	    public PokerDataHandler getPokerDataHandler() {
	    	return dh;
	    }
	    
	

	
}