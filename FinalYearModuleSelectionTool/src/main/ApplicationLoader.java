 
package main;

import controller.FinalYearOptionsController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.FinalYearOptionsRootPane;

public class ApplicationLoader extends Application {

	private FinalYearOptionsRootPane view;
	
	@Override
	public void init() {
		//create view and model and pass their references to the controller
		view = new FinalYearOptionsRootPane();
		StudentProfile model = new StudentProfile();
		new FinalYearOptionsController(model, view);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//sets min width and height for the stage window
		stage.setMinWidth(550); 
		stage.setMinHeight(550);
		
		stage.setWidth(750);
        stage.setHeight(575);
		
		stage.setTitle("Final Year Module Selection Tool");
		stage.setScene(new Scene(view));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
