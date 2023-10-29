package CSE360Project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class FileDirectoryPrototype extends Application {

    private Map<String, VirtualDirectory> virtualFileSystem = new HashMap<>();
    private VirtualDirectory rootDirectory = new VirtualDirectory("Root");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Directory Prototype");

        VBox vbox = new VBox(10);

        Button createFolderButton = new Button("Create Folder");
        Button listContentsButton = new Button("List Contents");
        Button searchFilesButton = new Button("Search Files");
        Button deleteFolderButton = new Button("Delete Folder");
        Button listAllFoldersButton = new Button("List All Folders");

        TextField folderNameInput = new TextField();
        TextField keywordInput = new TextField();

        TextArea outputTextArea = new TextArea();
        outputTextArea.setWrapText(true);

        virtualFileSystem.put(rootDirectory.getName(), rootDirectory);

        createFolderButton.setOnAction(e -> {
            String folderName = folderNameInput.getText();
            createVirtualFolder(folderName, outputTextArea);
        });

        listContentsButton.setOnAction(e -> {
            listVirtualContents(outputTextArea);
        });

        searchFilesButton.setOnAction(e -> {
            String keyword = keywordInput.getText();
            searchVirtualFiles(keyword, outputTextArea);
        });

        deleteFolderButton.setOnAction(e -> {
            String folderName = folderNameInput.getText();
            deleteVirtualFolder(folderName, outputTextArea);
        });

        listAllFoldersButton.setOnAction(e -> {
            listAllVirtualFolders(outputTextArea);
        });

        vbox.getChildren().addAll(
                createFolderButton,
                new Label("Folder Name:"),
                folderNameInput,
                listContentsButton,
                searchFilesButton,
                new Label("Search Keyword:"),
                keywordInput,
                deleteFolderButton,
                listAllFoldersButton,
                new Label("Output:"),
                outputTextArea
        );

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
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
