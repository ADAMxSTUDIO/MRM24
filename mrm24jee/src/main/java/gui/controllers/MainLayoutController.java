package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import gui.JavaFXMain;

public class MainLayoutController {

    @FXML
    private Button patientButton;

    @FXML
    private Button personnelButton;

    @FXML
    private Button rendezVousButton;

    @FXML
    private Button chambreButton;

    private JavaFXMain mainApp;

    @FXML
    public void initialize() {
        // Event handlers for buttons
        patientButton.setOnAction(e -> mainApp.showPatientView());
        personnelButton.setOnAction(e -> mainApp.showPersonnelView());
        rendezVousButton.setOnAction(e -> mainApp.showRendezVousView());
        chambreButton.setOnAction(e -> mainApp.showChambreView());
    }

    public void setMainApp(JavaFXMain mainApp) {
        this.mainApp = mainApp;
    }
}
