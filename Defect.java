package CSE360Project;

public class Defect {
	private String logUser;
	private String _project;
	private String _defectName;
	private String _defectInfor;
	private String dStatus;
	private String _inject;
	private String _removed;
	private String _category;
	public Defect(String user, String project, String defectName, String dInfo, String status, String inject, String removed, String category) {
		_project = project;
		_defectName = defectName;
		_defectInfor = dInfo;
		dStatus = status;
		_inject = inject;
		_removed = removed;
		_category = category;
	}
	
	public String getProject() {
		return _project;
	}
	
	public String getDefect() {
		return _defectName;
	}
	
	public String getDefectInfo() {
		return _defectInfor;
	}
	
	public String getDefectStatus() {
		return dStatus;
	}
	
	public String get_inject() {
		return _inject;
	}
	
	public String get_removed() {
		return _removed;
	}
	
	public String get_category() {
		return _category;
	}
	
	public void setProject(String newProject) {
		_project = newProject;
	}
	
	public void setDefect(String defect) {
		_defectName = defect;
	}
	
	public void setDefectInfo(String info) {
		_defectInfor = info;
	}
	
	public void setDefectStatus(String status) {
		dStatus = status;
	}
	
	public void set_inject(String inject) {
		_inject = inject;
	}
	
	public void set_removed(String removed) {
		_removed = removed;
	}
	
	public void set_category(String category) {
		_category = category;
	}
}
