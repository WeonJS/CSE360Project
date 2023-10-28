package CSE360Project;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class DataHandler {
	
	private Path directoryPath;
	
	public DataHandler(Path _directoryPath) {
		directoryPath = _directoryPath;
	}
	
	public void storeEffort(Effort effort) {
		// get hashed username
		Login loginSession = EffortLogger.getInstance().getLogin();
		String hashedUsername = loginSession.getHashedUsername();
		
		// navigate to directory for this user's effort logs
		Path userDirectoryPath = Paths.get(directoryPath + hashedUsername + "\\");
		
		// if user doesn't have a directory, make one
		if (Files.notExists(userDirectoryPath)) {
			userDirectoryPath.toFile().mkdir();
		}
		
		// calculate the new effort file's name based on how many efforts this user already has
		String effortFileName = hashedUsername;
		int effortCount = 0;
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(userDirectoryPath)) {
			// for each file's respective path in the user's directory
            for (Path filePath : directoryStream) {
            	String fileName = filePath.getName(filePath.getNameCount() - 1).toString();
            	
            	// get the section of the filename representing the username hash of its creator
            	String fileHash = fileName.substring(0, fileName.indexOf('_'));
            	
            	// compare the effort's user to the user of the file's creator
            	if (fileHash.equals(hashedUsername)) {
            		// if match, incremenmt effort count of that user
            		effortCount++;
            	}
            }
            
            // append file number to file name
            effortFileName += "_" + effortCount + ".csv";
            
            // delete effort if already exists, like in the case of editing an effort.
            File effortFile = new File(directoryPath.toString() + effortFileName);
            Files.deleteIfExists(effortFile.toPath());
            
            // create file
    		effortFile = Files.createFile(Paths.get(directoryPath.toString() + effortFileName)).toFile();
    		
    		// generate and encrypt effort's CSV data.
    		String effortCSV = effort.toCSVData();
    		
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static SecretKey generateAESKey() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, secureRandom);
        return keyGenerator.generateKey();
    }

    public static String encryptAES(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return new String(encryptedBytes);
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
logs in, their client will retrieve all of the efforts which they own, as specified in the effort files themselves using the user’s hashed login password
which is associated with the user who created the effort, and that hashed login password lies within the effort file.

Who will be responsible for this prototype? Keon Davoudi is responsible for this prototype.
Explain what the prototype will do. The prototype will demonstrate the DataHandler operations while taking into account the risks associated with it. It will 
be able to encrypt and store an effort file with the creator’s login password hashed within the file, and also decrypt and retrieve an effort file only if the 
user who created that file requests to retrieve it by comparing the current user’s hashed password and the hashed password in any given file.

How will the prototype mitigate the risk? The prototype will mitigate the risk by implementing checks that the user requesting to retrieve and decrypt a file 
is indeed the owner of that file, and otherwise will deny the request. Additionally, it will encrypt and store effort files, and it will include the user who 
created the effort’s password in the file as a hashed line within the file.
*/