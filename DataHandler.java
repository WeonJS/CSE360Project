package CSE360Project;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class DataHandler {
	
	private Path directoryPath;
	
	// keeps track of efforts that have been altered/created by the user
	private ArrayList<Effort> updatedEfforts = new ArrayList<>();
	
	// keeps track of all user efforts
	private ArrayList<Effort> userEfforts = new ArrayList<>();
	
	public DataHandler(Path _directoryPath) {
		directoryPath = _directoryPath;
		userEfforts = retrieveEfforts();
	}
	
	// returns an arraylist for each file in the current user's effort folder
	public ArrayList<Effort> retrieveEfforts() {
		ArrayList<Effort> efforts = new ArrayList<>();
		// get hashed username
		Login loginSession = EffortLogger.getInstance().getLogin();
		String hashedUsername = loginSession.getHashedUsername();
		
		// navigate to directory for this user's effort logs
		System.out.println(directoryPath.toString()+hashedUsername);
		Path userDirectoryPath = Paths.get(directoryPath.toString(), hashedUsername);
		
		try {
			// if the effortlogger data path does not exist, make it
			if (Files.notExists(directoryPath)) {
				Files.createDirectories(directoryPath);
			}
			
			// if user doesn't have a directory, make one
			if (Files.notExists(userDirectoryPath)) {
				Files.createDirectories(userDirectoryPath);
				return efforts;
			}
			
			// populate array of user efforts
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(userDirectoryPath);
			for (Path filePath : directoryStream) {
				userEfforts.add(Effort.constructFromCSVFile(filePath));
			}
			System.out.println("Loaded " + userEfforts.size() + " efforts for this user.");
    		
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return efforts;
	}
	
	public void storeEfforts(ArrayList<Effort> efforts) {
		// get hashed username
		Login loginSession = EffortLogger.getInstance().getLogin();
		String hashedUsername = loginSession.getHashedUsername();
		
		// navigate to directory for this user's effort logs
		Path userDirectoryPath = Paths.get(directoryPath.toString(), hashedUsername);
		
		for (Effort effort : efforts) {
			String effortFileName = "E " + effort.getUUID().toString();
			Path file = Paths.get(userDirectoryPath.toString(), effortFileName);
			String effortCSV = effort.toCSVData();
			
			try {
	            Files.write(file, effortCSV.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
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