package CSE360Project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class ASUHelloWorldJavaFX extends Application {
	String name = "Keon Davoudi";
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
    	System.out.println(name + " Hello World!");
    	System.out.println("It started!");
    	
    	for (int i = 0; i < 20; i++) {
    		System.out.println("lmfao" + i);
    	}
		
		for (int j = 0; j < 30; j++) {
			System.out.print("lmfao");
		}
		
		System.out.println("more stuff");
    	
        primaryStage.setTitle(name + ": Hello World");
        Button btn = new Button();
        System.out.print("jastons change");
        btn.setText("Display: '"+name+" says: Hello World!'");
        btn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                System.out.println(name + ": Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}