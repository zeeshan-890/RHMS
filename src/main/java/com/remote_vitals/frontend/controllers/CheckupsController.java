package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.CheckUp;
import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.CheckUpService;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.entities.Patient;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CheckupsController extends BaseController {

    @FXML
    private TableView<Checkup> checkupsTable;

    @FXML
    private TableColumn<Checkup, String> doctorNameColumn;

    @FXML
    private TableColumn<Checkup, String> checkupDateColumn;

    @FXML
    private TableColumn<Checkup, Void> actionsColumn;
    @FXML   Button goBackButton;

    @FXML
    public void initialize() {
        setupTableColumns();
        fetchCheckupData();
    }

    private void setupTableColumns() {
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        checkupDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkupDate"));
        actionsColumn.setCellFactory(getActionsCellFactory());
    }

private void fetchCheckupData() {
    new Thread(() -> {
        try {
            // Get beans from the Spring context
            Patient patient = (Patient) StaticDataClass.context.getBean(UserService.class).getCurrentUser().get();
            CheckUpService checkUpService = StaticDataClass.context.getBean(CheckUpService.class);


            

            // Fetch checkups for the patient from the backend service
            List<CheckUp> backendCheckups =
                    checkUpService.getAllCheckUpsOf(patient.getId(), Patient.class);

            // Map backend checkups to UI-friendly Checkup model
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            List<Checkup> uiCheckups = backendCheckups.stream()
                .map(c -> new Checkup(
                        c.getDoctor().getFirstName() + " " + c.getDoctor().getLastName(),
                        c.getTimeWhenMade().format(formatter),
                        c.getPrescription(),
                        c.getFeedback()
                ))
                .collect(Collectors.toList());

            // Update the TableView on the JavaFX Application thread
            Platform.runLater(() -> {
                ObservableList<Checkup> checkupList = FXCollections.observableArrayList(uiCheckups);
                checkupsTable.setItems(checkupList);
            });

        } catch (Exception e) {
            // Log the error and notify the user
            e.printStackTrace();
            Platform.runLater(() -> {
                System.out.println("Error loading checkup data: " + e.getMessage());
            });
        }
    }).start();
}

    private Callback<TableColumn<Checkup, Void>, TableCell<Checkup, Void>> getActionsCellFactory() {
        return param -> new TableCell<>() {
            private final Button viewButton = new Button("View");

            {
                viewButton.setOnAction(event -> {
                    Checkup checkup = getTableView().getItems().get(getIndex());
                    System.out.println("View button clicked for checkup: " + checkup);
                    try {
                        navigateToCheckupDetails(checkup);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        };
    }

    @FXML
    private void handleGoBack() {
        try{
            navigateToScene(goBackButton,"/fxml/patient.fxml", "Patient Dashboard");
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Go Back button clicked");
        // TODO: Implement go back logic, such as scene navigation
    }

    // Frontend Checkup model used by the TableView
    public static class Checkup {
        private String doctorName;
        private String checkupDate;
        private String prescription;
        private String feedback;

        public Checkup() {}

        public Checkup(String doctorName, String checkupDate, String prescription, String feedback) {
            this.doctorName = doctorName;
            this.checkupDate = checkupDate;
            this.prescription = prescription;
            this.feedback = feedback;
        }


        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getCheckupDate() {
            return checkupDate;
        }

        public void setCheckupDate(String checkupDate) {
            this.checkupDate = checkupDate;
        }
        public String getPrescription()
        {return prescription;}
        public void setPrescription(String prescription)
        {this.prescription = prescription;}
        public String getFeedback()
        {return feedback;}
        public void setFeedback(String feedback)
        {this.feedback = feedback;}

        @Override
        public String toString() {
            return "Checkup{" +
                    "doctorName='" + doctorName + '\'' +
                    ", checkupDate='" + checkupDate + '\'' +
                    ", prescription='" + prescription + '\'' +
                    ", feedback='" + feedback + '\'' +
                    '}';
        }
    }

    // Add a method to navigate to the CheckupDetails page
    private void navigateToCheckupDetails(Checkup checkup) throws Exception {
        // Assuming you have a method to load a new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Checkup_details.fxml"));
        Parent root = loader.load();

        // Get the controller and pass the checkup details
        CheckupDetailsController controller = loader.getController();
        controller.setCheckupDetails(checkup);

        // Set the scene and show it
        Stage stage = (Stage) checkupsTable.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Checkup Details");
        stage.show();
    }
}