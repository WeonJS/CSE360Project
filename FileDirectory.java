package CSE360Project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDirectory {

    public static boolean createFolder(Path p) {
    	try {
    		if (Files.notExists(p)) {
    			Files.createDirectories(p);
    		}
    		return true;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public static boolean fileExists(Path p) {
    	if (Files.exists(p)) {
			return true;
		}
    	return false;
    }
    
    public static boolean deleteFolder(Path p) {
    	if (!Files.isDirectory(p)) {
    		return false;
    	}
    	
    	try {
    		if (Files.exists(p)) {
    			Files.delete(p);
    		}
    		return true;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public static boolean deleteFile(Path p) {
    	
    	if (Files.isDirectory(p)) {
    		return false;
    	}
    	
    	try {
    		if (Files.exists(p)) {
    			Files.delete(p);
    		}
    		return true;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    // overrides the content of the file with the given string data.
    public static boolean writeToFile(Path p, String data) {
    	try {
            Files.write(p, data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return false;
    }
    
    public static List<String> getFileLines(Path p) {
    	try {
    		return Files.readAllLines(p);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    	
    }

}
