package CSE360Project;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class EffortLogger extends Application {
	
	// singleton instance
	private static EffortLogger instance;
	
	private Login loginSession;
	
	@Override
	public void start(Stage primaryStage) {
		if (instance == null)
			instance = this;
		else
			return;
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static EffortLogger getInstance() {
		return instance;
	}
	
	public Login getLogin() {
		return loginSession;
	}
	
}
