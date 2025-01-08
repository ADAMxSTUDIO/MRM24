package gui.controllers;

import dao.PersonnelDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Personnel;

import java.util.List;

public class PersonnelController {

    @FXML
    private TableView<Personnel> personnelTable;

    @FXML
    private TableColumn<Personnel, Long> idColumn;

    @FXML
    private TableColumn<Personnel, String> nameColumn;

    @FXML
    private TableColumn<Personnel, String> prenomColumn;

    @FXML
    private TableColumn<Personnel, String> jobTitleColumn; // Changed from functionTitleColumn

    @FXML
    private TableColumn<Personnel, String> specialtyColumn;

    @FXML
    private TableColumn<Personnel, String> phoneColumn;

    @FXML
    private TableColumn<Personnel, String> emailColumn;

    @FXML
    private VBox formBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField jobTitleField; // Changed from functionField

    @FXML
    private TextField specialtyField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    private final PersonnelDAO personnelDAO = new PersonnelDAO();
    private final ObservableList<Personnel> personnelList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle")); // Changed from functionTitle
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Load data into table
        loadPersonnel();
        formBox.setVisible(false);
    }

    private void loadPersonnel() {
        List<Personnel> personnel = personnelDAO.getAll();
        if (personnel != null) {
            personnelList.setAll(personnel);
            personnelTable.setItems(personnelList);
        } else {
            showAlert("Erreur", "Impossible de charger le personnel.");
        }
    }

    @FXML
    private void handleAddPersonnel() {
        clearForm();
        personnelTable.getSelectionModel().clearSelection();
        formBox.setVisible(true);
    }

    @FXML
    private void handleEditPersonnel() {
        Personnel selected = personnelTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fillForm(selected);
            formBox.setVisible(true);
        } else {
            showAlert("Erreur", "Aucun personnel sélectionné pour modification.");
        }
    }

    @FXML
    private void handleDeletePersonnel() {
        Personnel selected = personnelTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            personnelDAO.delete(selected.getId());
            loadPersonnel();
        } else {
            showAlert("Erreur", "Aucun personnel sélectionné pour suppression.");
        }
    }

    @FXML
    private void handleSavePersonnel() {
        String firstName = nameField.getText();
        String lastName = prenomField.getText();
        String jobTitle = jobTitleField.getText(); // Changed from functionField
        String speciality = specialtyField.getText();
        String phoneNumber = phoneField.getText();
        String email = emailField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || jobTitle.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            showAlert("Erreur", "Tous les champs obligatoires doivent être remplis.");
            return;
        }

        Personnel personnel = new Personnel();
        personnel.setFirstName(firstName);
        personnel.setLastName(lastName);
        personnel.setJobTitle(jobTitle); // Changed from functionTitle
        personnel.setSpeciality(speciality);
        personnel.setPhoneNumber(phoneNumber);
        personnel.setEmail(email);

        Personnel selected = personnelTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            personnelDAO.add(personnel);
        } else {
            personnelDAO.modify(personnel, selected.getId());
        }

        loadPersonnel();
        formBox.setVisible(false);
    }

    @FXML
    private void handleCancelEdit() {
        formBox.setVisible(false);
    }

    private void clearForm() {
        nameField.clear();
        prenomField.clear();
        jobTitleField.clear(); // Changed from functionField
        specialtyField.clear();
        phoneField.clear();
        emailField.clear();
    }

    private void fillForm(Personnel personnel) {
        nameField.setText(personnel.getFirstName());
        prenomField.setText(personnel.getLastName());
        jobTitleField.setText(personnel.getJobTitle()); // Changed from functionTitle
        specialtyField.setText(personnel.getSpeciality());
        phoneField.setText(personnel.getPhoneNumber());
        emailField.setText(personnel.getEmail());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
