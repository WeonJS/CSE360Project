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
    
    public static boolean deleteFolder(Path p) {
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
    
    public static boolean writeToPath(Path p, String data) {
    	try {
            Files.write(p, data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return false;
    }

}
