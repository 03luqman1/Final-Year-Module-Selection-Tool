package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.*;
import model.Module;

public class OverviewSelectionPane extends GridPane {

	private TextArea txtProfile, txtSelectedModules, txtReservedModules;
	private Button btnSaveOverview;
	private Label lblMessage;

	public OverviewSelectionPane() {

		// Styling
		this.setVgap(25);
		this.setHgap(25);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(25)); // Add padding

		// Create label
		lblMessage = new Label("");

		// Create text areas
		txtProfile = new TextArea("Name: \nP-Number: \nEmail: \nDate: \nCourse: ");
		txtProfile.setPrefSize(1000, 200); // Set preferred height for
		txtProfile.setMinHeight(100);
		txtProfile.setEditable(false);
		txtSelectedModules = new TextArea("Selected Modules:" + "\n" + "=============" + "\n");
		txtSelectedModules.setPrefSize(1000, 1000); // Set preferred height for
		txtSelectedModules.setEditable(false);
		txtReservedModules = new TextArea("Reserved Modules:" + "\n" + "=============" + "\n");
		txtReservedModules.setPrefSize(1000, 1000); // Set preferred height for
		txtReservedModules.setEditable(false);

		// Create button
		btnSaveOverview = new Button("Save Overview");

		// https://o7planning.org/10625/javafx-hbox-vbox
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
		// https://stackoverflow.com/questions/25513711/how-to-make-my-textfield-bigger-for-gui-in-java

		// Create HBox for the row
		HBox hbox = new HBox(25, txtSelectedModules, txtReservedModules);
		hbox.setAlignment(Pos.CENTER);

		// Create VBox
		VBox vbox = new VBox(25, txtProfile);
		vbox.setAlignment(Pos.CENTER);

		// Create HBox for the button
		HBox hboxButton = new HBox(btnSaveOverview);
		hboxButton.setAlignment(Pos.CENTER);

		// Create HBox for the label
		HBox hboxMessage = new HBox(lblMessage);
		hboxMessage.setAlignment(Pos.CENTER);

		// Add components to the grid pane
		this.add(vbox, 0, 0);
		this.add(hbox, 0, 1);
		this.add(hboxButton, 0, 2);
		this.add(hboxMessage, 0, 3);

	}

	// Methods to clear text areas
	public void clearSelectedOverview() {
		txtSelectedModules.setText("Selected Modules:" + "\n" + "=============" + "\n");
	}

	public void clearReservedOverview() {
		txtReservedModules.setText("Reserved Modules:" + "\n" + "=============" + "\n");
	}

	public void clearAllData() {
		txtProfile.clear();
		txtSelectedModules.setText("Selected Modules:" + "\n" + "=============" + "\n");
		txtReservedModules.setText("Reserved Modules:" + "\n" + "=============" + "\n");
	}

	// Methods to update text areas
	public void updateSelectedOverview(Collection<Module> modules) {
		String modulesString = ("Selected Modules:" + "\n" + "=============" + "\n");

		for (Module module : modules) {
			modulesString += module.OverviewString();
		}
		txtSelectedModules.setText(modulesString);
	}

	public void updateReservedOverview(Collection<Module> modules) {
		String modulesString = ("Reserved Modules:" + "\n" + "=============" + "\n");
		for (Module module : modules) {
			modulesString += module.OverviewString();
		}
		txtReservedModules.setText(modulesString);
	}

	public void updateProfileOverview(String Name, String PNo, String Email, String Date, String Course) {
		txtProfile.setText("Name: " + Name + "\nP-Number: " + PNo + "\nEmail: " + Email + "\nDate: " + Date
				+ "\nCourse: " + Course);
	}

	public void setMessage(String msg) {
		lblMessage.setText(msg);
	}

	// method to attach button event handler
	public void addSaveOverviewButtonHandler(EventHandler<ActionEvent> handler) {
		btnSaveOverview.setOnAction(handler);
	}

	// Methods to return text areas
	public String getProfileOverview() {
		return txtProfile.getText();
	}

	public String getSelectedOverview() {
		return txtSelectedModules.getText();
	}

	public String getReservedOverview() {
		return txtReservedModules.getText();
	}

}
