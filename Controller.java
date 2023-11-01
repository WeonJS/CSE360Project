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
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;

public class Controller implements Initializable{

	@FXML
	private ComboBox<String> projectComboBox;
	@FXML
	private ComboBox<String> lifeCycleComboBox;
	@FXML
	private ComboBox<String> effortCatComboBox;
	@FXML
	private ComboBox<String> deliverableComboBox;
	@FXML
	private ComboBox<String> lifeCycleComboBox2;
	@FXML
	private ComboBox<String> effortCatComboBox2;
	@FXML
	private ComboBox<String> editEffortComboBox;
	@FXML
	private ComboBox<String> dropDown_Defects;
	@FXML
	private Label projDefectsLabel;
	@FXML
	private Label errorLabel;
	@FXML
	private Text projStatus; // to be used later cause i aint doin allat yet
	@FXML
	private Label successLabel;
	@FXML
	private TextField defectEntry;
	@FXML 
	private Text defectNum;
	@FXML
	private Text saveStatus;
	@FXML
	private Text defectStatus;
	@FXML
	private TextArea defectInfo;
	@FXML
	private Label editErrorLabel;
	@FXML
	private Label editSuccessLabel;
	@FXML
	private ListView<String> stepsInjected;
	@FXML
	private ListView<String> stepsRemoved;
	@FXML
	private ListView<String> defectCat;
	@FXML
	private TextField editDate;
	@FXML
	private TextField editStartTime;
	@FXML
	private TextField editEndTime;
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
	@FXML
	private Text loginMessage1;
	@FXML
	private Pane createAccountView;
	@FXML
	private TextField usernameField2;
	@FXML
	private PasswordField passwordField2;
	@FXML
	private Pane pokerFirstView;
	@FXML
	private Pane pokerSecondView;
	@FXML
	private TextField IDField;
	@FXML
	private TextField passwordSessionField;
	@FXML
	private TextField IDField2;
	@FXML
	private TextField passwordSessionField2;
	@FXML
	private TextField topicsField;
	@FXML
	private ComboBox<String> activeSessions; 
	@FXML
	private Text createMessage;
	@FXML
	private Text createMessage2;
	
	@FXML
	private ComboBox<String> selectDefectCombo;

	
	private boolean effortInProgress = false;

	
	//private String loggedUser = "jmattoka"; 
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String newDefect;
	
	
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
	    stepsInjected.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding", 
	    		"Conceptual Design Plan", 
	    		"Requirements", 
	    		"Conceptual Design",
	    		"Conceptual Design Review",
	    		"Conceptual Design Plan",
	    		"Conceptual Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation"));
	    stepsRemoved.setItems(FXCollections.observableArrayList(
	    		"Problem Understanding", 
	    		"Conceptual Design Plan", 
	    		"Requirements", 
	    		"Conceptual Design",
	    		"Conceptual Design Review",
	    		"Conceptual Design Plan",
	    		"Conceptual Design/Prototype",
	    		"Detailed Design Review",
	    		"Implementation Plan",
	    		"Test Case Generation",
	    		"Solution Specification",
	    		"Solution Review",
	    		"Solution Implementation"));
	    defectCat.setItems(FXCollections.observableArrayList(
	    		"Not Specified",
	    		"10 Documentation",
	    		"20 Syntax",
	    		"30 Build, Package",
	    		"40 Assignment",
	    		"50 something idk",
	    		"60 Checking",
	    		"70 Data",
	    		"80 Function",
	    		"90 System",
	    		"100 Environment"));
	    loggedInView.setVisible(false);
	    loginView.setVisible(true);
	    createAccountView.setVisible(false);
	    pokerSecondView.setVisible(false);
	    pokerFirstView.setVisible(true);
	    
	    
	}
	@FXML
	private void startEffort(Event e) {
		if(!effortInProgress) {
			effortInProgress = true;
			startTime = LocalDateTime.now();
			successLabel.setText("Effort Started at " + startTime);
		}
		else
			errorLabel.setText("ERROR: Effort Already Started");
	}
	
	@FXML
	private void endEffort(Event e) {
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
		String username = usernameField2.getText();
		String password = passwordField2.getText();
		Login login = EffortLogger.getInstance().getLogin();
		boolean success = login.handleLoginAttempt(username, password);
		int loginAttempts = EffortLogger.getInstance().getLogin().getAttempts();
		if (success) {
			successfulLogin();
			loginView.setVisible(false);
			loggedInView.setVisible(true);
		} else if (loginAttempts < Login.MAX_ATTEMPTS) {
			loginMessage1.setText("Login attempt failed. "+(Login.MAX_ATTEMPTS - loginAttempts)+" left.");
		} else {
			loginMessage1.setText("Run out of attempts. Please try again later.");
		}
	}
	
	
	@FXML
	private void attemptJoin() {
		String ID = activeSessions.getValue();
		String name = IDField2.getText();
		String password = passwordSessionField2.getText();
		PlanningPoker planningPoker = new PlanningPoker();
		boolean success = planningPoker.handleJoinAttempt(ID, password);
		if (success) {
			//DO THINGS FOR PLANNING POKER
			createMessage2.setText("SUCCESS");
		} else {
			createMessage2.setText("Invlid Combination");
		}
	}
	
	
	@FXML
	private void changeToCreateView() {
		
			loginView.setVisible(false);
			createAccountView.setVisible(true);
		
	}
	
	@FXML
	private void changeToLoginView() {
		
		loginView.setVisible(true);
		createAccountView.setVisible(false);
	
    }
	
	
	@FXML
	private void changePokerCreate() {
		
			pokerFirstView.setVisible(false);
			pokerSecondView.setVisible(true);
		
	}
	
	@FXML
	private void changePokerJoin() {
		
		pokerFirstView.setVisible(true);
		pokerSecondView.setVisible(false);
	
    }
	
	
	
	@FXML
	private void createSession() {
		String ID = IDField.getText();
		String password = passwordSessionField.getText();
		String topics = topicsField.getText();
		PlanningPoker planningPoker = new PlanningPoker();
		String msg = planningPoker.attemptCreateSession(ID, password, topics);
		activeSessions.getItems().add(ID);
		createMessage.setText(msg);
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
	private void editEffort(Event e) {
		if(sanitizeEditEffort()) {
			editSuccessLabel.setText("Effort successfully editted");
			editErrorLabel.setText("");
			String startTime = editEffortComboBox.getValue();				//find the effort object by its start time
			LocalDateTime oldStartTime = LocalDateTime.parse(startTime);
			Effort oldEffort = EffortLogger.getInstance().getEffortDataHandler().getEffort(oldStartTime);
			//construct new start time and end time
			String updatedStartTime = editDate.getText() + "T" + editStartTime.getText();
			String updatedEndTime = editDate.getText() + "T" + editEndTime.getText();
			LocalDateTime newStartTime = LocalDateTime.parse(updatedStartTime);
			LocalDateTime newEndTime = LocalDateTime.parse(updatedEndTime);
			//grab comboBox updated info
			String updatedLifeCycleStep = lifeCycleComboBox2.getValue();
			String updatedEffortCat = effortCatComboBox2.getValue();
			
			Effort editedEffort = new Effort(oldEffort.getUserID(), 
											  newStartTime, 
											  newEndTime, 
											  updatedLifeCycleStep,
											  oldEffort.getProjectType(),
											  updatedEffortCat,
											  oldEffort.getDeliverableType());
			//edit effort in file directory
			EffortLogger.getInstance().getEffortDataHandler().updateEffort(oldEffort, editedEffort);

			//repopulate combobox with updated information
			ArrayList<Effort> userEffort = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
		    ArrayList<String> displayData = new ArrayList<String>();
		    for(Effort i: userEffort) {
		    	displayData.add(i.getStartTime().toString());
		    }
		    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));
			
			
		}
	}
	
	private boolean sanitizeCreateEffortData() {
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
	
	
	private boolean sanitizeEditEffort() {
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
	
	private boolean sanitizeUserInput() {
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
		String datePatternRegex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
		String timePatternRegex = "^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
		Pattern datePattern = Pattern.compile(datePatternRegex);
		Pattern timePattern = Pattern.compile(timePatternRegex);
		Matcher matcher = datePattern.matcher(dateValue);
		if(!matcher.matches()) {			//authenticate data
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
	    ArrayList<String> displayData = new ArrayList<String>();
	    for(Effort i: userEffort) {
	    	displayData.add(i.getStartTime().toString());
	    }
	    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));
	}
	
	@FXML
	void createDefect(Event e) {
		Defect def = new Defect(dropDown_Defects.getValue(), "-new defect-", defectInfo.getText(), "Open", " ", " ", " ");
		EffortLogger.getInstance().getEffortDataHandler().addDefect(def);
		ArrayList<Defect> defectArr = EffortLogger.getInstance().getEffortDataHandler().getDefectArray();
		ArrayList<String> defectStrings = new ArrayList<String>();
		for (Defect d : defectArr) {
			defectStrings.add(d.getDefectString());
		}
		selectDefectCombo.setItems(FXCollections.observableArrayList(defectStrings));
		defectStatus.setText(def.getDefectStatus());
	}	
	
	@FXML
	void updateDefect(Event e) {
		if (sanitizeUpdateDefect()) {
			if (defectEntry.getText() != null) {
				String injected;
				String removed;
				String category;
				Defect oldDefect = EffortLogger.getInstance().getEffortDataHandler().getDef(selectDefectCombo.getValue());
				if (stepsInjected.getSelectionModel().getSelectedItem() == null) {
					injected = "";
				}
				else {
					injected = stepsInjected.getSelectionModel().getSelectedItem();
				}
				if (stepsRemoved.getSelectionModel().getSelectedItem() == null) {
					removed = "";
				}
				else {
					removed = stepsRemoved.getSelectionModel().getSelectedItem();
				}
				if (defectCat.getSelectionModel().getSelectedItem() == null) {
					category = "";
				}
				else {
					category = defectCat.getSelectionModel().getSelectedItem();
				}
				
				Defect newDef = new Defect(dropDown_Defects.getValue(), defectEntry.getText(), defectInfo.getText(), oldDefect.getDefectStatus(), injected, removed, category);
				
				EffortLogger.getInstance().getEffortDataHandler().replaceDefect(oldDefect, newDef);
				ArrayList<Defect> defectArr = EffortLogger.getInstance().getEffortDataHandler().getDefectArray();
				ArrayList<String> defectStrings = new ArrayList<String>();
				for (Defect d : defectArr) {
					defectStrings.add(d.getDefectString());
				}
				selectDefectCombo.setItems(FXCollections.observableArrayList(defectStrings));
			}
		}
	}
	
	@FXML
	void displayMessage(Event e) {
		saveStatus.setText("");
		saveStatus.setText("Changes Unsaved");
	}
	
	boolean sanitizeUpdateDefect() {
		if (defectEntry.getText().isEmpty() && defectInfo.getText().isEmpty() && stepsInjected.getSelectionModel().getSelectedItem() == null && stepsRemoved.getSelectionModel().getSelectedItem() == null && defectCat.getSelectionModel().getSelectedItem() == null) {
			saveStatus.setText("");
			saveStatus.setText("Fields empty");
			return false;
		}
		return true;
	}
}	
	
