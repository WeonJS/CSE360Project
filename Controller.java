package CSE360Project;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Controller implements Initializable{

	@FXML
	private ComboBox<String> projectComboBox = new ComboBox<String>();
	@FXML
	private ComboBox<String> lifeCycleComboBox = new ComboBox<String>();
	@FXML
	private ComboBox<String> effortCatComboBox = new ComboBox<String>();
	@FXML
	private ComboBox<String> deliverableComboBox = new ComboBox<String>();
	@FXML
	private ComboBox<String> lifeCycleComboBox2 = new ComboBox<String>();
	@FXML
	private ComboBox<String> effortCatComboBox2 = new ComboBox<String>();
	@FXML
	private ComboBox<String> editEffortComboBox = new ComboBox<String>();
	@FXML
	private ComboBox<String> dropDown_Defects = new ComboBox<String>();
	@FXML
	private Label projDefectsLabel = new Label();
	@FXML
	private Label errorLabel = new Label();
	@FXML
	private Label successLabel = new Label();
	@FXML
	private Label editErrorLabel = new Label();
	@FXML
	private Label editSuccessLabel = new Label();
	@FXML
	private TextField editDate = new TextField();
	@FXML
	private TextField editStartTime = new TextField();
	@FXML
	private TextField editEndTime = new TextField();
	@FXML
	private TabPane loggedInView;
	@FXML
	private Pane loginView;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Text loginMessage;
	
	private boolean effortInProgress = false;
	
	//private String loggedUser = "jmattoka"; 
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    projectComboBox.setItems(FXCollections.observableArrayList("Development Project", "Business Project"));
	    lifeCycleComboBox.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding",
	    		"Conceptual Design Plan",
	    		"Requirements",
	    		"Concetpual Design",
	    		"Conceptual Design Review",
	    		"Detailed Design Plan",
	    		"Detailed Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation",
	    		"Unit/System Test",
	    		"Reflection",
	    		"Repository Update"));
	    effortCatComboBox.setItems(FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Others"));
	    deliverableComboBox.setItems(FXCollections.observableArrayList(
	    		"Conceptual Design",
	    		"Detailed Design",
	    		"Test Cases",
	    		"Solution",
	    		"Reflection",
	    		"Outline",
	    		"Draft Report",
	    		"User Defined",
	    		"Other"));
	    dropDown_Defects.setItems(FXCollections.observableArrayList("Development Project", "Business Project"));
	    lifeCycleComboBox2.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding",
	    		"Conceptual Design Plan",
	    		"Requirements",
	    		"Concetpual Design",
	    		"Conceptual Design Review",
	    		"Detailed Design Plan",
	    		"Detailed Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation",
	    		"Unit/System Test",
	    		"Reflection",
	    		"Repository Update"));
	    effortCatComboBox2.setItems(FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Others"));
	    
	    loggedInView.setVisible(false);
	    loginView.setVisible(true);
	    
	    
	}
	@FXML
	void startEffort(Event e) {
		if(!effortInProgress) {
			effortInProgress = true;
			startTime = LocalDateTime.now();
			successLabel.setText("Effort Started at " + startTime);
		}
		else
			errorLabel.setText("ERROR: Effort Already Started");
	}
	
	@FXML
	void endEffort(Event e) {
		boolean cleanInput = sanitizeCreateEffortData();
		if(effortInProgress && cleanInput) {
			effortInProgress = false;
			endTime = LocalDateTime.now();
			errorLabel.setText("");
			successLabel.setText("Effort ended at " + endTime);
			//CREATE THE OBJECT
			String loggedUser = EffortLogger.getInstance().getLogin().getLoginSession().getHashedUser(); //identifier of effort creator
			System.out.print(loggedUser);
			Effort newEffort = new Effort(loggedUser, startTime, endTime, lifeCycleComboBox.getValue(), 
										  projectComboBox.getValue(), effortCatComboBox.getValue(), 
										  deliverableComboBox.getValue());
			
			// add the new effort to the updated effort list
			EffortLogger.getInstance().getEffortDataHandler().addToUpdatedEfforts(newEffort);
			EffortLogger.getInstance().getEffortDataHandler().addToUserEfforts(newEffort);
			
		    ArrayList<Effort> userEffort = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
		    ArrayList<String> displayData = new ArrayList<String>();
		    for(Effort i: userEffort) {
		    	displayData.add(i.getStartTime().toString());
		    }
		    
		    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));
		}
		else {
			if(cleanInput) {
				successLabel.setText("");
				errorLabel.setText("ERROR: No effort started");
			}
		}
	}
	
	@FXML
	private void attemptLogin() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		Login login = EffortLogger.getInstance().getLogin();
		boolean success = login.handleLoginAttempt(username, password);
		int loginAttempts = EffortLogger.getInstance().getLogin().getAttempts();
		if (success) {
			successfulLogin();
			loginView.setVisible(false);
			loggedInView.setVisible(true);
		} else if (loginAttempts < Login.MAX_ATTEMPTS) {
			loginMessage.setText("Login attempt failed. "+(Login.MAX_ATTEMPTS - loginAttempts)+" left.");
		} else {
			loginMessage.setText("Run out of attempts. Please try again later.");
		}
	}
	
	@FXML
	private void createLogin() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		Login login = EffortLogger.getInstance().getLogin();
		String msg = login.attemptCreateAccount(username, password);
		loginMessage.setText(msg);
	}
	
	@FXML
	void editEffort(Event e) {
		if(sanitizeEditEffort()) {
			editSuccessLabel.setText("Effort successfully editted");
			editErrorLabel.setText("");
			
		}
	}
	
	private boolean sanitizeCreateEffortData(){
		if(projectComboBox.getValue() == null || 
		   effortCatComboBox.getValue() == null ||
		   lifeCycleComboBox.getValue() == null || 
		   deliverableComboBox.getValue() == null) 
		{
			errorLabel.setText("ERROR: One of the Fields is left blank");
			return false;
		}
		
		return true;
	}
	
	
	boolean sanitizeEditEffort() {
		if(effortCatComboBox2.getValue() == null ||
		   lifeCycleComboBox2.getValue() == null ||
		   editEffortComboBox.getValue() == null ||
		   editDate.getText() == null ||
		   editStartTime.getText() == null ||
		   editEndTime.getText() == null)   
		{
			editSuccessLabel.setText("");
			editErrorLabel.setText("ERROR: One of the fields is left blank");
			return false;
		}
		if(!sanitizeUserInput()) {
			editSuccessLabel.setText("");
			editErrorLabel.setText("ERROR: Invalid Input");
			return false;
		}
		return true;
	}
	
	boolean sanitizeUserInput() {
		final int MAX_DATE_LENGTH = 10;
		final int MAX_TIME_LENGTH = 8;
		String dateValue = editDate.getText();
		String startValue = editStartTime.getText();
		String endValue = editEndTime.getText();
		
		if(dateValue.length() > MAX_DATE_LENGTH ||
		   startValue.length() > MAX_TIME_LENGTH ||
		   endValue.length() > MAX_TIME_LENGTH) 
		{
			return false;
		}
		
		//gonna cook this rn
		String datePatternRegex = "\\d{4}-\\d{2}-\\d{2}";
		String timePatternRegex = "\\d{2}:\\d{2}:\\d{2}";
		Pattern datePattern = Pattern.compile(datePatternRegex);
		Pattern timePattern = Pattern.compile(timePatternRegex);
		Matcher matcher = datePattern.matcher(dateValue);
		if(!matcher.matches()) {			//authenticate data
			System.out.print("FAIL HERE");
			return false;
		}
		matcher = timePattern.matcher(startValue); //WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
		if(!matcher.matches())
			return false;
		
		matcher = timePattern.matcher(endValue);
		if(!matcher.matches())
			return false;
		
		return true;
	}
	
	
	
	private void successfulLogin() {
		ArrayList<Effort> userEffort = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
	    System.out.print(userEffort.size());
	    ArrayList<String> displayData = new ArrayList<String>();
	    for(Effort i: userEffort) {
	    	displayData.add(i.getStartTime().toString());
	    }
	    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));
	}
	
}
