package CSE360Project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class FileDirectoryPrototype {

    private Map<String, VirtualDirectory> virtualFileSystem = new HashMap<>();
    private VirtualDirectory rootDirectory = new VirtualDirectory("Root");

    public FileDirectoryPrototype() {
    	virtualFileSystem.put(rootDirectory.getName(), rootDirectory);
    }

    public void createVirtualFolder(String folderName, TextArea outputTextArea) {
        if (!virtualFileSystem.containsKey(folderName)) {
            VirtualDirectory newFolder = new VirtualDirectory(folderName);
            virtualFileSystem.put(newFolder.getName(), newFolder);
            outputTextArea.appendText("Virtual folder created successfully: " + newFolder.getName() + "\n");
        } else {
            outputTextArea.appendText("Virtual folder already exists: " + folderName + "\n");
        }
    }

    public void listVirtualContents(TextArea outputTextArea) {
        outputTextArea.appendText("Virtual File Directory Contents:\n");
        for (VirtualDirectory directory : virtualFileSystem.values()) {
            outputTextArea.appendText(directory.getName() + "\n");
        }
    }

    public void searchVirtualFiles(String keyword, TextArea outputTextArea) {
        outputTextArea.appendText("Virtual Files matching the keyword '" + keyword + "':\n");
        for (VirtualDirectory directory : virtualFileSystem.values()) {
            if (directory.getName().contains(keyword)) {
                outputTextArea.appendText(directory.getName() + "\n");
            }
        }
    }

    public void deleteVirtualFolder(String folderName, TextArea outputTextArea) {
        if (virtualFileSystem.containsKey(folderName)) {
            virtualFileSystem.remove(folderName);
            outputTextArea.appendText("Virtual folder deleted successfully: " + folderName + "\n");
        } else {
            outputTextArea.appendText("Virtual folder not found: " + folderName + "\n");
        }
    }

    public void listAllVirtualFolders(TextArea outputTextArea) {
        outputTextArea.appendText("All Virtual Folders:\n");
        for (VirtualDirectory directory : virtualFileSystem.values()) {
            outputTextArea.appendText(directory.getName() + "\n");
        }
    }

    class VirtualDirectory {
        private String name;

        public VirtualDirectory(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
