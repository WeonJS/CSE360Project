package CSE360Project;

import java.time.LocalDateTime;

public class Effort {
	private String lifeCycleStep;
	private String projectType;
	private String effortCategory;
	private String deliverableType;
	private final String userID;
	
	private int duration;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public Effort(String username, LocalDateTime _startTime, LocalDateTime _endTime, String _lifeCycleStep, String _projectType, String _effortCategory, String _deliverableType) {
		userID = username;
		startTime = _startTime;
		endTime = _endTime;
		duration = endTime.getSecond() - startTime.getSecond();
		
		lifeCycleStep = _lifeCycleStep;
		projectType = _projectType;
		effortCategory = _effortCategory;
		deliverableType = _deliverableType;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int d) {
		duration = d;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalDateTime t) {
		startTime = t;
	}
	
	public LocalDateTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalDateTime t) {
		endTime = t;
	}
	
	public String getLifeCycleStep() {
		return lifeCycleStep;
	}
	
	public void setLifeCycleStep(String value) {
		lifeCycleStep = value;
	}
	
	public String getProjectType() {
		return projectType;
	}
	
	public void setProjectType(String value) {
		projectType = value;
	}
	
	public String getEffortCategory() {
		return effortCategory;
	}
	
	public void setEffortCategory(String value) {
		effortCategory = value;
	}
	
	public String getDeliverableType() {
		return deliverableType;
	}
	
	public void setDeliverableType(String value) {
		deliverableType = value;
	}
	@Override 
	public String toString() {
		String result = "\nUsername " + userID
						+ "\nStart Time: " + startTime
						+ "\nEnd Time: " + endTime
						+ "\nDuration: " + duration
						+ "\nLifeCycleStep: " + lifeCycleStep
						+ "\nProjectType: " + projectType
						+ "\nEffortCategory: " + effortCategory
						+ "\nDeliverableType: " + deliverableType;
						
		return result;
	}
	
	
	
	
	
	
	
	
	
}
