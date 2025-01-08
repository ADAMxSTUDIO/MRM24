package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXMain extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MRM24 - Maison de Retraite Médicalisée");

        initMainLayout();
    }

    private void initMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/views/MainLayout.fxml"));
            mainLayout = loader.load();

            // Set up the controller
            gui.controllers.MainLayoutController controller = loader.getController();
            controller.setMainApp(this); // Pass this instance of JavaFXMain to the controller

            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Load the default view
            showPatientView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPatientView() {
        loadView("PatientView.fxml");
    }

    public void showPersonnelView() {
        loadView("PersonnelView.fxml");
    }

    public void showRendezVousView() {
        loadView("RendezVousView.fxml");
    }

    public void showChambreView() {
        loadView("ChambreView.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/views/" + fxmlFile));
            BorderPane view = loader.load();

            // Set the view in the center of the main layout
            mainLayout.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
