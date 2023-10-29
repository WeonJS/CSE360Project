package CSE360Project;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
	
	private boolean effortInProgress = false;
	
	private String loggedUser = "jmattoka";
	
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
	    
	    ArrayList<Effort> userEffort = EffortLogger.getInstance().getEffortDataHandler().getUserEffortArray();
	    System.out.print(userEffort.size());
	    ArrayList<String> displayData = new ArrayList<String>();
	    for(Effort i: userEffort) {
	    	displayData.add(i.getStartTime().toString());
	    }
	    editEffortComboBox.setItems(FXCollections.observableArrayList(displayData));
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
	
	boolean sanitizeCreateEffortData(){
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
	
	@FXML
	void editEffort() {
		if(sanitizeEditEffort()) {
			editSuccessLabel.setText("Effort successfully editted");
			editErrorLabel.setText("");
		}
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
		return true;
	}
	
	
}
