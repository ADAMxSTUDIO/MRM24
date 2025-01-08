package gui.controllers;

import dao.PatientDAO;
import dao.PersonnelDAO;
import dao.RendezVousDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Patient;
import model.Personnel;
import model.RendezVous;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RendezVousController {

    @FXML
    private TableView<RendezVous> rendezVousTable;

    @FXML
    private TableColumn<RendezVous, Long> idColumn;

    @FXML
    private TableColumn<RendezVous, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<RendezVous, String> patientColumn;

    @FXML
    private TableColumn<RendezVous, String> personnelColumn;

    @FXML
    private TableColumn<RendezVous, String> reasonColumn;

    @FXML
    private VBox formBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private ComboBox<Personnel> personnelComboBox;

    @FXML
    private TextField reasonField;

    private final RendezVousDAO rendezVousDAO = new RendezVousDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final PersonnelDAO personnelDAO = new PersonnelDAO();

    private final ObservableList<RendezVous> rendezVousList = FXCollections.observableArrayList();
    private final ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private final ObservableList<Personnel> personnelList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configureComboBoxes();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        patientColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getPatient().getFirstName() + " " + r.getValue().getPatient().getLastName()));
        personnelColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getPersonnel().getFirstName() + " " + r.getValue().getPersonnel().getLastName()));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        loadRendezVous();
        loadPatients();
        loadPersonnel();

        formBox.setVisible(false);
    }

    private void configureComboBoxes() {
        patientComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Patient patient) {
                return (patient == null) ? "" : patient.getFirstName() + " " + patient.getLastName();
            }

            @Override
            public Patient fromString(String string) {
                return null;
            }
        });

        personnelComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Personnel personnel) {
                return (personnel == null) ? "" : personnel.getFirstName() + " " + personnel.getLastName();
            }

            @Override
            public Personnel fromString(String string) {
                return null;
            }
        });
    }

    private void loadRendezVous() {
        List<RendezVous> rendezVous = rendezVousDAO.getAll();
        if (rendezVous != null) {
            rendezVousList.setAll(rendezVous);
            rendezVousTable.setItems(rendezVousList);
        }
    }

    private void loadPatients() {
        List<Patient> patients = patientDAO.getAll();
        if (patients != null) {
            patientList.setAll(patients);
            patientComboBox.setItems(patientList);
        }
    }

    private void loadPersonnel() {
        List<Personnel> personnel = personnelDAO.getAll();
        if (personnel != null) {
            personnelList.setAll(personnel);
            personnelComboBox.setItems(personnelList);
        }
    }

    @FXML
    private void handleAddRendezVous() {
        formBox.setVisible(true);
        clearForm();
    }

    @FXML
    private void handleEditRendezVous() {
        RendezVous selected = rendezVousTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            formBox.setVisible(true);
            datePicker.setValue(selected.getDateTime().toLocalDate());
            timeField.setText(selected.getDateTime().toLocalTime().toString());
            patientComboBox.setValue(selected.getPatient());
            personnelComboBox.setValue(selected.getPersonnel());
            reasonField.setText(selected.getReason());
        } else {
            showAlert("Erreur", "Veuillez sélectionner un rendez-vous.");
        }
    }

    @FXML
    private void handleSaveRendezVous() {
        if (datePicker.getValue() == null || timeField.getText().isEmpty() || patientComboBox.getValue() == null || personnelComboBox.getValue() == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(timeField.getText(), formatter);
            LocalDateTime dateTime = LocalDateTime.of(datePicker.getValue(), time);

            Patient patient = patientComboBox.getValue();
            Personnel personnel = personnelComboBox.getValue();
            String reason = reasonField.getText();

            RendezVous rendezVous = new RendezVous(patient, personnel, dateTime, reason);

            RendezVous selected = rendezVousTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                rendezVousDAO.add(rendezVous);
            } else {
                rendezVousDAO.modify(rendezVous, selected.getId());
            }

            loadRendezVous();
            formBox.setVisible(false);
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "Format de l'heure invalide. Utilisez le format HH:mm.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors de l'enregistrement.");
        }
    }

    @FXML
    private void handleDeleteRendezVous() {
        RendezVous selected = rendezVousTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            rendezVousDAO.delete(selected.getId());
            loadRendezVous();
        } else {
            showAlert("Erreur", "Veuillez sélectionner un rendez-vous.");
        }
    }

    @FXML
    private void handleCancelEdit() {
        formBox.setVisible(false);
        clearForm();
    }

    private void clearForm() {
        datePicker.setValue(null);
        timeField.clear();
        patientComboBox.setValue(null);
        personnelComboBox.setValue(null);
        reasonField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
