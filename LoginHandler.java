package CSE360Project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Nicholas Lorenzini 

public class LoginHandler extends Application {
	public TabPane tabPane = new TabPane();
	private int passwordAttempts = 0;
	static int MAX_ATTEMPTS = 5;
	Database db = new Database();
	
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");
        
        // reset the current amount of password attempts on start
        passwordAttempts = 0;
        
        // setup for login tab and create account tab
        this.tabPane.getTabs().addAll(createLoginTab(), createAccountTab());
        this.tabPane.setTabMinWidth(345);

        Scene scene = new Scene(this.tabPane, 700, 270);
        
        // link to CSS stylesheet
        scene.getStylesheets().add("LoginHandler/styles.css");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab createLoginTab() {
        Tab loginTab = new Tab("Login");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        Label loginLabel = new Label("LOG IN"); 
        loginLabel.getStyleClass().add("login-label");
        
        Label createLabel = new Label("Click here to create an account");
        createLabel.getStyleClass().add("create-label");
        
        Label resetPassLabel = new Label("Reset Password");
        resetPassLabel.getStyleClass().add("reset-label");
        
        
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");
        
        createLabel.setOnMouseClicked(event -> {
        	this.tabPane = (TabPane) createLabel.getScene().getRoot();
            this.tabPane.getSelectionModel().select(createAccountTab());
        });

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

           //authentication logic
            handleLoginAttempt(username, password);
            usernameField.clear();
            passwordField.clear();
           
        });

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        
  
        //organize label above grid of login
       
        VBox root = new VBox();
        
        root.getChildren().addAll(loginLabel, grid, resetPassLabel, createLabel);
        
        VBox.setMargin(loginLabel, new Insets(20,20,2,110));
        VBox.setMargin(createLabel, new Insets(10,20,0,55));
        VBox.setMargin(resetPassLabel, new Insets(0,20,0,90));
        
        loginTab.setContent(root);
        return loginTab;
    }

    private Tab createAccountTab() {
        Tab createAccountTab = new Tab("Create Account");
  
  
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(35, 20, 20, 20));

        Label newUsernameLabel = new Label("New Username:");
        TextField newUsernameField = new TextField();
        Label newUsernameCondition = new Label("8-16 characters, Letters/Numbers and - or _ allowed");
        HBox newUsernameBox = new HBox(5, newUsernameField, newUsernameCondition);

        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();
        Label newPasswordCondition = new Label("8-16 characters, At least one: Capital Letter, Number, Special Character");
        HBox newPasswordBox = new HBox(5, newPasswordField, newPasswordCondition);

        Button createAccountButton = new Button("Create Account");
        createAccountButton.getStyleClass().add("create-button");

        createAccountButton.setOnAction(event -> {
            String newUsername = newUsernameField.getText();
            String newPassword = newPasswordField.getText();

            // account creation logic
            createAccount(newUsername, newPassword);
            newUsernameField.clear();
            newPasswordField.clear();
        });

        grid.add(newUsernameLabel, 0, 0);
        grid.add(newUsernameBox, 1, 0);
        grid.add(newPasswordLabel, 0, 1);
        grid.add(newPasswordBox, 1, 1);
        grid.add(createAccountButton, 1, 2);

        createAccountTab.setContent(grid);
        return createAccountTab;
    }

    
    private void handleLoginAttempt(String username, String password) {
    	
    	//in practice will have a reset timer for login attempts
    	if(passwordAttempts >= MAX_ATTEMPTS) {
    		showAlert("Too many failed attemps", "Please try again later");
    		return;
    	}

        // Check the login credentials with the UsernameDatabase
        boolean isValidLogin = db.confirmExistingUser(username, password);

        if (isValidLogin) {
            showAlert("Login Successful", "Welcome, " + username + "!");
        } else {
            passwordAttempts++;
            showAlert("Login Failed", "Invalid username or password. Attempt #" + passwordAttempts);
        }
    }

    
    private void createAccount(String newUsername, String newPassword) {
        // Check if the new username already exists
        if (db.existingUsername(newUsername)) {
            showAlert("Account Creation Failed", "Username already exists. Please login instead.");
        } 
        //check to see if username is valid input
        else if (!db.isValidUsername(newUsername)) {
            showAlert("Account Creation Failed", "Invalid Username. Please refer to username requirements");
        }
        //check to see if password is valid input
        else if (!db.isValidPassword(newPassword)) {
        	showAlert("Account Creation Failed", "Invalid Password. Please refer to password requirements");
        }
        else {
            // Add the new username and password to the database
            db.addUser(newUsername, newPassword);
            showAlert("Account Created", "Account for " + newUsername + " has been created.");
        }
    }
    
    
   
    //Alert creation function
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
