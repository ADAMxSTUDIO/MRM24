package gui.controllers;

import dao.PatientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PatientController {

    @FXML
    private TableView<Patient> patientTable;

    @FXML
    private TableColumn<Patient, Long> idColumn;

    @FXML
    private TableColumn<Patient, String> nameColumn;

    @FXML
    private TableColumn<Patient, String> prenomColumn;

    @FXML
    private TableColumn<Patient, LocalDate> dateOfBirthColumn;

    @FXML
    private TableColumn<Patient, String> addressColumn;

    @FXML
    private TableColumn<Patient, String> phoneColumn;

    @FXML
    private TableColumn<Patient, String> medicalHistoryColumn;

    @FXML
    private VBox formBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField dateOfBirthField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea medicalHistoryField;

    private final PatientDAO patientDAO = new PatientDAO();
    private final ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        medicalHistoryColumn.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));

        // Load data into table
        loadPatients();
        formBox.setVisible(false);
    }

    private void loadPatients() {
        List<Patient> patients = patientDAO.getAll();
        if (patients != null) {
            patientList.setAll(patients);
            patientTable.setItems(patientList);
        } else {
            showAlert("Erreur", "Impossible de charger les patients.");
        }
    }

    @FXML
    private void handleAddPatient() {
        clearForm();
        patientTable.getSelectionModel().clearSelection();
        formBox.setVisible(true);
    }

    @FXML
    private void handleEditPatient() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fillForm(selected);
            formBox.setVisible(true);
        } else {
            showAlert("Erreur", "Aucun patient sélectionné pour modification.");
        }
    }

    @FXML
    private void handleDeletePatient() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            patientDAO.delete(selected.getId());
            loadPatients();
        } else {
            showAlert("Erreur", "Aucun patient sélectionné pour suppression.");
        }
    }

    @FXML
    private void handleSavePatient() {
        String firstName = nameField.getText();
        String lastName = prenomField.getText();
        String dateOfBirthText = dateOfBirthField.getText();
        String address = addressField.getText();
        String phoneNumber = phoneField.getText();
        String medicalHistory = medicalHistoryField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirthText.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Erreur", "Tous les champs obligatoires doivent être remplis.");
            return;
        }

        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(dateOfBirthText);
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "La date de naissance doit être au format YYYY-MM-DD.");
            return;
        }

        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setDateOfBirth(dateOfBirth);
        patient.setAddress(address);
        patient.setPhoneNumber(phoneNumber);
        patient.setMedicalHistory(medicalHistory);

        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            patientDAO.add(patient);
        } else {
            patientDAO.modify(patient, selected.getId());
        }

        loadPatients();
        formBox.setVisible(false);
    }

    @FXML
    private void handleCancelEdit() {
        formBox.setVisible(false);
    }

    private void clearForm() {
        nameField.clear();
        prenomField.clear();
        dateOfBirthField.clear();
        addressField.clear();
        phoneField.clear();
        medicalHistoryField.clear();
    }

    private void fillForm(Patient patient) {
        nameField.setText(patient.getFirstName());
        prenomField.setText(patient.getLastName());
        dateOfBirthField.setText(patient.getDateOfBirth().toString());
        addressField.setText(patient.getAddress());
        phoneField.setText(patient.getPhoneNumber());
        medicalHistoryField.setText(patient.getMedicalHistory());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
