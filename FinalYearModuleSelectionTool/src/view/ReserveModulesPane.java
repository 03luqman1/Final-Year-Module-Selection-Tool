package view;

import java.util.Collection;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Module;

public class ReserveModulesPane extends GridPane {

	private ListView<Module> lstUnselectedT1, lstReservedT1, lstUnselectedT2, lstReservedT2;
	private Button btnT1Add, btnT1Remove, btnT1Confirm, btnT2Add, btnT2Remove, btnT2Confirm;
	private Label lblMessage1, lblMessage2;
	private TitledPane titledPaneT1, titledPaneT2;

	public ReserveModulesPane() {

		// Styling
		this.setVgap(25);
		this.setHgap(25);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(25));

		// Create an accordion
		Accordion accordion = new Accordion();

		// Create labels
		Label lblUnselectedT1 = new Label("Unselected Term 1 Modules ");
		Label lblReservedT1 = new Label("Reserved Term 1 Modules");
		Label lblReserveT1Comment = new Label("Reserve 30 credits worth of term 1 modules");
		Label lblUnselectedT2 = new Label("Unselected Term 2 Modules ");
		Label lblReservedT2 = new Label("Reserved Term 2 Modules");
		Label lblReserveT2Comment = new Label("Reserve 30 credits worth of term 2 modules");
		lblMessage1 = new Label("");
		lblMessage2 = new Label("");

		// Setup list view
		lstUnselectedT1 = new ListView<Module>();
		lstUnselectedT1.setPrefSize(1000, 1000);
		lstReservedT1 = new ListView<Module>();
		lstReservedT1.setPrefSize(1000, 1000);
		lstUnselectedT2 = new ListView<Module>();
		lstUnselectedT2.setPrefSize(1000, 1000);
		lstReservedT2 = new ListView<Module>();
		lstReservedT2.setPrefSize(1000, 1000);

		// Initialise create profile button
		btnT1Add = new Button("Add");
		btnT1Remove = new Button("Remove");
		btnT1Confirm = new Button("Confirm");
		btnT2Add = new Button("Add");
		btnT2Remove = new Button("Remove");
		btnT2Confirm = new Button("Confirm");

		// grow accordion based on window size
		GridPane.setHgrow(accordion, Priority.ALWAYS);
		GridPane.setVgrow(accordion, Priority.ALWAYS);

		// Create titled panes for Term 1 and Term 2 modules
		titledPaneT1 = new TitledPane();
		titledPaneT2 = new TitledPane();
		titledPaneT1.setText("Term 1 Modules");
		titledPaneT2.setText("Term 2 Modules");

		// Create VBox containers for the content of each titled pane
		VBox vboxT1 = new VBox(25);
		VBox vboxT2 = new VBox(25);

		//https://o7planning.org/10625/javafx-hbox-vbox
		//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
		// Create a GridPane to hold the content of the VBox for Term 1 Modules
		GridPane gridPaneT1 = new GridPane();
		gridPaneT1.setHgap(25);
		gridPaneT1.setVgap(25);
		gridPaneT1.setAlignment(Pos.CENTER);

		// Add lblUnselectedT1 and lstUnselectedT1 to the first column
		gridPaneT1.add(lblUnselectedT1, 0, 0);
		gridPaneT1.add(lstUnselectedT1, 0, 1);

		// Add lblReservedT1 and lstReservedT1 to the second column
		gridPaneT1.add(lblReservedT1, 1, 0);
		gridPaneT1.add(lstReservedT1, 1, 1);

		// Create HBox containers for controls below the list boxes
		HBox hboxControlsT1 = new HBox(25);
		hboxControlsT1.setAlignment(Pos.CENTER);
		hboxControlsT1.getChildren().addAll(lblReserveT1Comment, btnT1Add, btnT1Remove, btnT1Confirm);
		HBox hboxMessage1 = new HBox(25);
		hboxMessage1.setAlignment(Pos.CENTER);
		hboxMessage1.getChildren().addAll(lblMessage1);

		// Add gridPaneT1 and hboxControls to vboxT1
		vboxT1.getChildren().addAll(gridPaneT1, hboxControlsT1, hboxMessage1);

		// Create a GridPane to hold the content of the VBox for Term 2 Modules
		GridPane gridPaneT2 = new GridPane();
		gridPaneT2.setHgap(25);
		gridPaneT2.setVgap(25);
		gridPaneT2.setAlignment(Pos.CENTER);

		// Add lblUnselectedT2 and lstUnselectedT2 to the first column
		gridPaneT2.add(lblUnselectedT2, 0, 0);
		gridPaneT2.add(lstUnselectedT2, 0, 1);

		// Add lblReservedT2 and lstReservedT2 to the second column
		gridPaneT2.add(lblReservedT2, 1, 0);
		gridPaneT2.add(lstReservedT2, 1, 1);

		// Create HBox containers for controls below the list boxes
		HBox hboxControlsT2 = new HBox(25);
		hboxControlsT2.setAlignment(Pos.CENTER);
		hboxControlsT2.getChildren().addAll(lblReserveT2Comment, btnT2Add, btnT2Remove, btnT2Confirm);
		HBox hboxMessage2 = new HBox(25);
		hboxMessage2.setAlignment(Pos.CENTER);
		hboxMessage2.getChildren().addAll(lblMessage2);

		// Add gridPaneT2 and hboxControls to vboxT2
		vboxT2.getChildren().addAll(gridPaneT2, hboxControlsT2, hboxMessage2);

		// Set the content of the titled panes as the VBox containers
		titledPaneT1.setContent(vboxT1);
		titledPaneT2.setContent(vboxT2);

		// Add the titled panes to the accordion
		accordion.getPanes().addAll(titledPaneT1, titledPaneT2);

		// Add the accordion to the grid pane
		this.add(accordion, 0, 0);

	}

	// methods to allow the controller to add / remove modules from the list views
	public void addModuleDataTolstUnselectedT1(Collection<Module> collection) {
		lstUnselectedT1.getItems().clear();
		lstUnselectedT1.getItems().addAll(collection);
	}

	public void addModuleDataTolstUnselectedT2(Collection<Module> collection) {
		lstUnselectedT2.getItems().clear();
		lstUnselectedT2.getItems().addAll(collection);
	}

	public void addModuleDataTolstReservedT1(Collection<Module> collection) {
		lstReservedT1.getItems().clear();
		lstReservedT1.getItems().addAll(collection);
	}

	public void addModuleDataTolstReservedT2(Collection<Module> collection) {
		lstReservedT2.getItems().clear();
		lstReservedT2.getItems().addAll(collection);
	}

	public void clearAllData() {
		lstUnselectedT1.getItems().clear();
		lstUnselectedT2.getItems().clear();
		lstReservedT1.getItems().clear();
		lstReservedT2.getItems().clear();
	}

	public void addT1ModuleToReserved(Module module) {
		lstReservedT1.getItems().add(module);
		lstUnselectedT1.getItems().remove(module);
	}

	public void addT2ModuleToReserved(Module module) {
		lstReservedT2.getItems().add(module);
		lstUnselectedT2.getItems().remove(module);
	}

	public void removeT1ModuleFromReserved(Module module) {
		lstReservedT1.getItems().remove(module);
		lstUnselectedT1.getItems().add(module);
	}

	public void removeT2ModuleFromReserved(Module module) {
		lstReservedT2.getItems().remove(module);
		lstUnselectedT2.getItems().add(module);
	}

	// methods to attach button event handlers
	public void addAddT1ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT1Add.setOnAction(handler);
	}

	public void addRemoveT1ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT1Remove.setOnAction(handler);
	}

	public void addConfirmT1ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT1Confirm.setOnAction(handler);
	}

	public void addAddT2ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT2Add.setOnAction(handler);
	}

	public void addRemoveT2ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT2Remove.setOnAction(handler);
	}

	public void addConfirmT2ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT2Confirm.setOnAction(handler);
	}

	// methods to retrieve the form selection input
	public List<Module> getReservedT1Modules() {
		return lstReservedT1.getItems();
	}

	public List<Module> getReservedT2Modules() {
		return lstReservedT2.getItems();
	}

	public Module getSelectedT1Module() {
		return lstUnselectedT1.getSelectionModel().getSelectedItem();
	}

	public Module getSelectedT2Module() {
		return lstUnselectedT2.getSelectionModel().getSelectedItem();
	}

	public Module getTheReservedT1Module() {
		return lstReservedT1.getSelectionModel().getSelectedItem();
	}

	public Module getTheReservedT2Module() {
		return lstReservedT2.getSelectionModel().getSelectedItem();
	}

	public int getReservedT1Credits() {
		List<Module> modules = lstReservedT1.getItems();
		int T1Credits = 0;
		for (Module module : modules) {
			T1Credits += module.getModuleCredits();
		}
		return T1Credits;
	}

	public int getReservedT2Credits() {
		List<Module> modules = lstReservedT2.getItems();
		int T2Credits = 0;
		for (Module module : modules) {
			T2Credits += module.getModuleCredits();
		}
		return T2Credits;
	}

	// Methods to set messages
	public void setMessage1(String msg) {
		lblMessage1.setText(msg);
	}

	public void setMessage2(String msg) {
		lblMessage2.setText(msg);
	}

	// Methods to clear all inputs
	public void clearAll() {
		lstUnselectedT1.getItems().clear();
		lstUnselectedT2.getItems().clear();
		lstReservedT1.getItems().clear();
		lstReservedT2.getItems().clear();
		lblMessage1.setText("");
		lblMessage2.setText("");
	}

	// Method to expand a specific accordion tab
	public void changePane(int index) {
		if (index == 0) {
			titledPaneT1.setExpanded(true);
		} else if (index == 1) {
			titledPaneT2.setExpanded(true);
		} else {
			titledPaneT1.setExpanded(false);
			titledPaneT2.setExpanded(false);
		}
	}

}
