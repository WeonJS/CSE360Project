package CSE360Project;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

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
	private Label errorLabel = new Label();
	@FXML
	private Label successLabel = new Label();
	
	private boolean effortInProgress = false;
	
	
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
	    
	}
	@FXML
	void startEffort(Event e) {
		if(!effortInProgress) {
			effortInProgress = true;
			LocalDateTime startTime = LocalDateTime.now();
			successLabel.setText("Effort Started at " + startTime);
		}
		else
			errorLabel.setText("ERROR: Effort Already Started");
	}
	
	@FXML
	void endEffort(Event e) {
		if(effortInProgress && sanitizeCreateEffortData()) {
			effortInProgress = false;
			LocalDateTime endTime = LocalDateTime.now();
			successLabel.setText("Effort ended at " + endTime);
		}
		else {
			successLabel.setText("");
			errorLabel.setText("ERROR: No effort started");
		}
	}
	
	boolean sanitizeCreateEffortData(){
		if(projectComboBox.getValue() == "Select..." || 
		   effortCatComboBox.getValue() == "Select..." ||
		   lifeCycleComboBox.getValue() == "Select..." || 
		   deliverableComboBox.getValue() == "Select...") 
		{
			errorLabel.setText("ERROR: One of the Fields is left blank");
			return false;
		}
		
		return true;
	}
	
	
}
