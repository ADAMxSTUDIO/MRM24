package gui.controllers;

import dao.ChambreDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Chambre;

import java.util.List;

public class ChambreController {

    @FXML
    private TableView<Chambre> chambreTable;

    @FXML
    private TableColumn<Chambre, Long> idColumn;

    @FXML
    private TableColumn<Chambre, String> roomNumberColumn;

    @FXML
    private TableColumn<Chambre, String> roomTypeColumn;

    @FXML
    private TableColumn<Chambre, Boolean> disponibleColumn;

    @FXML
    private VBox formBox;

    @FXML
    private TextField roomNumberField;

    @FXML
    private ComboBox<String> roomTypeComboBox;

    @FXML
    private CheckBox disponibleCheckBox;

    private final ChambreDAO chambreDAO = new ChambreDAO();
    private final ObservableList<Chambre> chambreList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        // Custom rendering for the "Disponible" column
        disponibleColumn.setCellValueFactory(new PropertyValueFactory<>("disponible")); // Changed from "isDisponible"
        disponibleColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Oui" : "Non"); // Display "Oui" for true and "Non" for false
                }
            }
        });


        // Load data into table
        loadChambres();
        formBox.setVisible(false);
    }

    private void loadChambres() {
        List<Chambre> chambres = chambreDAO.getAll();
        if (chambres != null) {
            chambreList.setAll(chambres);
            chambreTable.setItems(chambreList);
        } else {
            showAlert("Erreur", "Impossible de charger les chambres.");
        }
    }

    @FXML
    private void handleAddChambre() {
        clearForm();
        chambreTable.getSelectionModel().clearSelection();
        formBox.setVisible(true);
    }

    @FXML
    private void handleEditChambre() {
        Chambre selected = chambreTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            fillForm(selected);
            formBox.setVisible(true);
        } else {
            showAlert("Erreur", "Aucune chambre sélectionnée pour modification.");
        }
    }

    @FXML
    private void handleDeleteChambre() {
        Chambre selected = chambreTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            chambreDAO.delete(selected.getId());
            loadChambres();
        } else {
            showAlert("Erreur", "Aucune chambre sélectionnée pour suppression.");
        }
    }

    @FXML
    private void handleSaveChambre() {
        String roomNumber = roomNumberField.getText();
        String roomType = roomTypeComboBox.getValue();
        boolean isDisponible = disponibleCheckBox.isSelected();

        if (roomNumber.isEmpty() || roomType == null) {
            showAlert("Erreur", "Tous les champs obligatoires doivent être remplis.");
            return;
        }

        Chambre chambre = new Chambre();
        chambre.setRoomNumber(roomNumber);
        chambre.setRoomType(roomType);
        chambre.setDisponible(isDisponible);

        Chambre selected = chambreTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            chambreDAO.add(chambre);
        } else {
            chambreDAO.modify(chambre, selected.getId());
        }

        loadChambres();
        formBox.setVisible(false);
    }

    @FXML
    private void handleCancelEdit() {
        formBox.setVisible(false);
    }

    private void clearForm() {
        roomNumberField.clear();
        roomTypeComboBox.setValue(null);
        disponibleCheckBox.setSelected(false);
    }

    private void fillForm(Chambre chambre) {
        roomNumberField.setText(chambre.getRoomNumber());
        roomTypeComboBox.setValue(chambre.getRoomType());
        disponibleCheckBox.setSelected(chambre.isDisponible());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}