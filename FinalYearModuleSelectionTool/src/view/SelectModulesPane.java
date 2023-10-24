package view;

import java.util.Collection;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class SelectModulesPane extends GridPane {

	private TextField txtT1Credits, txtT2Credits;
	private Button btnT1Add, btnT1Remove, btnT2Add, btnT2Remove, btnReset, btnSubmit;
	private ListView<Module> lstUnselectedT1, lstUnselectedT2, lstSelectedT1, lstSelectedT2, lstSelectedYear;
	private Label lblMessage;

	public SelectModulesPane() {

		// Styling
		this.setVgap(25);
		this.setHgap(25);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(25));

		// Create labels
		Label lblUnselectedT1 = new Label("Unselected Term 1 Modules ");
		Label lblT1 = new Label("Term 1 ");
		Label lblUnselectedT2 = new Label("Unselected Term 2 Modules ");
		Label lblT2 = new Label("Term 2 ");
		Label lblT1Credits = new Label("Current Term 1 Credits: ");
		Label lblYearModule = new Label("Selected Year Long Module: ");
		lblYearModule.setMinWidth(160);
		Label lblSelectedT1 = new Label("Selected Term 1 Modules ");
		Label lblSelectedT2 = new Label("Selected Term 2 Modules ");
		Label lblT2Credits = new Label("Current Term 2 Credits: ");
		lblMessage = new Label();

		// Setup text fields
		txtT1Credits = new TextField();
		txtT2Credits = new TextField();
		txtT1Credits.setEditable(false);
		txtT2Credits.setEditable(false);
		txtT1Credits.setPrefWidth(50);
		txtT2Credits.setPrefWidth(50);

		// Setup list views
		lstUnselectedT1 = new ListView<>();
		lstUnselectedT1.setPrefSize(800, 500);
		lstUnselectedT2 = new ListView<>();
		lstUnselectedT2.setPrefSize(800, 500);
		lstSelectedT1 = new ListView<>();
		lstSelectedT1.setPrefSize(800, 500);
		lstSelectedT2 = new ListView<>();
		lstSelectedT2.setPrefSize(800, 500);
		lstSelectedYear = new ListView<>();
		lstSelectedYear.setMinSize(300, 25);
		lstSelectedYear.setPrefSize(300, 25);

		// Initialise buttons
		btnT1Add = new Button("Add");
		btnT1Remove = new Button("Remove");
		btnT2Add = new Button("Add");
		btnT2Remove = new Button("Remove");
		btnReset = new Button("Reset");
		btnSubmit = new Button("Submit");

		//https://o7planning.org/10625/javafx-hbox-vbox
		//https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
		HBox hboxYearModule = new HBox(10, lblYearModule, lstSelectedYear);
		GridPane.setColumnSpan(hboxYearModule, 2);
		hboxYearModule.setAlignment(Pos.CENTER);
		this.add(hboxYearModule, 0, 0);

		VBox vboxT1 = new VBox(10, lblUnselectedT1, lstUnselectedT1, lblSelectedT1, lstSelectedT1);
		this.add(vboxT1, 0, 1);

		VBox vboxT2 = new VBox(10, lblUnselectedT2, lstUnselectedT2, lblSelectedT2, lstSelectedT2);
		this.add(vboxT2, 1, 1);

		HBox hboxT1Buttons = new HBox(10, lblT1, btnT1Add, btnT1Remove);
		hboxT1Buttons.setAlignment(Pos.CENTER);
		this.add(hboxT1Buttons, 0, 2);

		HBox hboxT2Buttons = new HBox(10, lblT2, btnT2Add, btnT2Remove);
		hboxT2Buttons.setAlignment(Pos.CENTER);
		this.add(hboxT2Buttons, 1, 2);

		HBox hboxT1Credits = new HBox(10, lblT1Credits, txtT1Credits);
		hboxT1Credits.setAlignment(Pos.CENTER);
		this.add(hboxT1Credits, 0, 3);

		HBox hboxT2Credits = new HBox(10, lblT2Credits, txtT2Credits);
		hboxT2Credits.setAlignment(Pos.CENTER);
		this.add(hboxT2Credits, 1, 3);

		HBox hboxBottom = new HBox(10, btnReset, btnSubmit);
		GridPane.setColumnSpan(hboxBottom, 2);
		hboxBottom.setAlignment(Pos.CENTER);
		this.add(hboxBottom, 0, 4);

		HBox hboxBottomMessage = new HBox(10, lblMessage);
		GridPane.setColumnSpan(hboxBottomMessage, 2);
		hboxBottomMessage.setAlignment(Pos.CENTER);
		this.add(hboxBottomMessage, 0, 5);

	}

	// methods to allow the controller to add / remove modules from the list views
	public void addModuleDataTolstUnselectedT1(Collection<Module> collection) {
		lstUnselectedT1.getItems().clear();
		lstUnselectedT1.getItems().addAll(collection);
	}

	public void addModuleDataTolstSelectedT1(Collection<Module> collection) {
		lstSelectedT1.getItems().clear();
		lstSelectedT1.getItems().addAll(collection);
	}

	public void addModuleDataTolstUnselectedT2(Collection<Module> collection) {
		lstUnselectedT2.getItems().clear();
		lstUnselectedT2.getItems().addAll(collection);
	}

	public void addModuleDataTolstSelectedT2(Collection<Module> collection) {
		lstSelectedT2.getItems().clear();
		lstSelectedT2.getItems().addAll(collection);
	}

	public void addModuleDataTolstYearModule(Collection<Module> collection) {
		lstSelectedYear.getItems().clear();
		lstSelectedYear.getItems().addAll(collection);
	}

	public void addT1ModuleToSelected(Module module) {
		lstSelectedT1.getItems().add(module);
		lstUnselectedT1.getItems().remove(module);
	}

	public void addT2ModuleToSelected(Module module) {
		lstSelectedT2.getItems().add(module);
		lstUnselectedT2.getItems().remove(module);
	}

	public void removeT1ModuleFromSelected(Module module) {
		lstSelectedT1.getItems().remove(module);
		lstUnselectedT1.getItems().add(module);
	}

	public void removeT2ModuleFromSelected(Module module) {
		lstSelectedT2.getItems().remove(module);
		lstUnselectedT2.getItems().add(module);
	}

	// Methods to change credits for each term
	public void addT1ModuleCredits(int T1Credits) {
		txtT1Credits.setText(String.valueOf(T1Credits));
	}

	public void addT2ModuleCredits(int T2Credits) {
		txtT2Credits.setText(String.valueOf(T2Credits));
	}

	// methods to attach the button event handlers
	public void addResetButtonHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}

	public void addAddT1ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT1Add.setOnAction(handler);
	}

	public void addAddT2ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT2Add.setOnAction(handler);
	}

	public void addRemoveT1ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT1Remove.setOnAction(handler);
	}

	public void addRemoveT2ButtonHandler(EventHandler<ActionEvent> handler) {
		btnT2Remove.setOnAction(handler);
	}

	public void addSubmitButtonHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}

	// methods to retrieve the form selection
	public Module getTheUnselectedT1Module() {
		return lstUnselectedT1.getSelectionModel().getSelectedItem();
	}

	public Module getTheUnselectedT2Module() {
		return lstUnselectedT2.getSelectionModel().getSelectedItem();
	}

	public Module getTheSelectedT1Module() {
		return lstSelectedT1.getSelectionModel().getSelectedItem();
	}

	public Module getTheSelectedT2Module() {
		return lstSelectedT2.getSelectionModel().getSelectedItem();
	}

	public List<Module> getSelectedT1Modules() {
		return lstSelectedT1.getItems();
	}

	public List<Module> getSelectedT2Modules() {
		return lstSelectedT2.getItems();
	}

	public List<Module> getSelectedYearModules() {
		return lstSelectedYear.getItems();
	}

	public int getTxtT1Credits() {
		String creditsText = txtT1Credits.getText();
		int credits = Integer.parseInt(creditsText);
		return credits;
	}

	public int getTxtT2Credits() {
		String creditsText = txtT2Credits.getText();
		int credits = Integer.parseInt(creditsText);
		return credits;
	}

	// Method to set text to the message label
	public void setMessage(String msg) {
		lblMessage.setText(msg);
	}

}