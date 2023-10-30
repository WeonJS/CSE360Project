package CSE360Project;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EffortDataHandler {
	
	private Path directoryPath;
	
	// keeps track of efforts that have been altered/created by the user
	private ArrayList<Effort> updatedEfforts = new ArrayList<>();
	
	// keeps track of all user efforts
	private ArrayList<Effort> userEfforts = new ArrayList<>();
	
	private ArrayList<Effort> toDeleteOnClose = new ArrayList<>();
	
	public EffortDataHandler() {
		directoryPath = EffortLogger.getInstance().getDataPathDirectory();
	}
	
	// retrieves and decrypts all the user's efforts and stores them into userEfforts arraylist.
	public boolean retrieveEfforts() {
		// get hashed username
		Login.LoginSession loginSession = EffortLogger.getInstance().getLogin().getLoginSession();
		String hashedUsername = loginSession.getHashedUser();
		
		// navigate to directory for this user's effort logs
		Path userDirectoryPath = Paths.get(directoryPath.toString(), hashedUsername);
		
		try {
			// if the effortlogger data path does not exist, make it
			FileDirectory.createFolder(directoryPath);
			
			// if user doesn't have a directory, make one
			FileDirectory.createFolder(userDirectoryPath);
			
			// populate array of user efforts
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(userDirectoryPath);
			for (Path filePath : directoryStream) {
				userEfforts.add(Effort.constructFromCSVFile(filePath));
			}
			System.out.println("Loaded " + userEfforts.size() + " efforts for this user.");
    		return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return false;
	}
	
	// stores runtime efforts of user which have been altered/created.
	public boolean storeEfforts() {
		// get hashed username
		Login.LoginSession loginSession = EffortLogger.getInstance().getLogin().getLoginSession();
		
		if (loginSession == null)
			return false;
		
		String hashedUsername = EffortLogger.getInstance().getLogin().getLoginSession().getHashedUser();
		
		// navigate to directory for this user's effort logs
		Path userDirectoryPath = Paths.get(directoryPath.toString(), hashedUsername);
		
		// store changed/created efforts
		for (Effort effort : updatedEfforts) {
			// uses the effort start date to uniquely identify each effort file name
			String effortIdentifier = effort.getStartTime().toString().replaceAll(":", "_");
			
			// "E" flag identifies that it is an effort
			String effortFileName = "E " + effortIdentifier;
			
			// convert class data to CSV string
			String effortCSV = effort.toCSVData();
			
			// write data to path
			Path file = Paths.get(userDirectoryPath.toString(), effortFileName);
			FileDirectory.writeToFile(file, effortCSV);
		}
		
		// delete the efforts to be deleted on close
		DirectoryStream<Path> directoryStream;
		ArrayList<String> deletedStartTimes = new ArrayList<>();
		for (Effort ef : toDeleteOnClose) {
			deletedStartTimes.add("E " + ef.getStartTime().toString().replaceAll(":", "_"));
			System.out.println("ADDED TO BE DELETED: " + ef);
		}
		
		try {
			directoryStream = Files.newDirectoryStream(userDirectoryPath);
			for (Path filePath : directoryStream) {
				String fileName = filePath.getName(filePath.getNameCount() - 1).toString();
				
				System.out.println(""+deletedStartTimes+" contains " + fileName + "? : " + (deletedStartTimes.contains(fileName)));
				if (deletedStartTimes.contains(fileName)) {
					
					FileDirectory.deleteFile(Paths.get(userDirectoryPath.toString(), fileName));
					System.out.println("deleted " + fileName + " from " + userDirectoryPath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void addToUpdatedEfforts(Effort e) {
		updatedEfforts.add(e);
	}
	
	public void addToUserEfforts(Effort e) {
		userEfforts.add(e);
	}
	
	public ArrayList<Effort> getUpdatedEfforts() {
		return updatedEfforts;
	}
	
	public ArrayList<Effort> getUserEffortArray(){
		return userEfforts;
	}
	
	public Path getRootDirectory() {
		return directoryPath;
	}
	
	public boolean removeEffort(Effort e) {
		for (Effort effort : userEfforts) {
			if (e.equals(effort)) {
				System.out.println("QUEUED TO BE DELETED " + e);
				toDeleteOnClose.add(effort);
				userEfforts.remove(effort);
				return true;
			}
		}
		return false;
	}
	
	public Effort getEffort(LocalDateTime start) {
		for (Effort e : userEfforts) {
			if (e.getStartTime().equals(start)) {
				return e;
			}
		}
		
		return null;
	}
	
	public void updateEffort(Effort oldEffort, Effort newEffort) {
		
		if (updatedEfforts.contains(oldEffort)) {
			updatedEfforts.remove(oldEffort);
			updatedEfforts.add(newEffort);
		} else {
			updatedEfforts.add(newEffort);
		}
		
		removeEffort(oldEffort);
		userEfforts.add(newEffort);
		
	}
}


/*
Notes:
- Effort file structure:
	- Name of file: "sha512'd username of creator" + "_" + "index of effort for that user, starting at 0"
	- Inside of file: encrypted EAS data of the original CSV file data.


Prototype 3: DataHandler
This prototype has the functionality of managing data as it is stored or retrieved from the file directory, as requested by a valid user. 

It uses the CryptoTools to decrypt or encrypt the file being requested in order to maintain secure data management of EffortLogger.

Unauthorized access to efforts which may not belong to a user. We do not want users to be able to retrieve and decrypt efforts which they did not create. 
Given that the effort files all exist in an encrypted state in the same directory, then we need to make sure that clients cannot access and decrypt effort 
files that are not theirs given the close proximity of all the effort files.

Storage of efforts within the directory which is mapped to the user who created it. Each effort in the directory must contain details so that when a user 
logs in, their client will retrieve all of the efforts which they own, as specified in the effort files themselves using the user�s hashed login password
which is associated with the user who created the effort, and that hashed login password lies within the effort file.

Who will be responsible for this prototype? Keon Davoudi is responsible for this prototype.
Explain what the prototype will do. The prototype will demonstrate the DataHandler operations while taking into account the risks associated with it. It will 
be able to encrypt and store an effort file with the creator�s login password hashed within the file, and also decrypt and retrieve an effort file only if the 
user who created that file requests to retrieve it by comparing the current user�s hashed password and the hashed password in any given file.

How will the prototype mitigate the risk? The prototype will mitigate the risk by implementing checks that the user requesting to retrieve and decrypt a file 
is indeed the owner of that file, and otherwise will deny the request. Additionally, it will encrypt and store effort files, and it will include the user who 
created the effort�s password in the file as a hashed line within the file.
*/