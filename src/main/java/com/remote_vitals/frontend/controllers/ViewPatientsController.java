package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.frontend.models.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class ViewPatientsController extends BaseController {

    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> firstnameColumn;
    @FXML
    private TableColumn<Patient, String> lastnameColumn;
    @FXML
    private TableColumn<Patient, String> genderColumn;
    @FXML
    private TableColumn<Patient, String> phoneNumberColumn;
    @FXML
    private TableColumn<Patient, String> emailColumn;
    @FXML
    private TableColumn<Patient, String> conditionColumn;
    @FXML
    Button goback;
    @FXML
    private TableColumn<Patient, Void> viewReportsColumn;




    @FXML
    public void initialize() {
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("medicalHistory")); // Assuming condition is stored in medicalHistory

        // Load patient data


        try {UserService userService = StaticDataClass.context.getBean(UserService.class);
            ObservableList<Patient> patients = FXCollections.observableArrayList(userService.getAllPatients().stream().map(this::convertToModel).collect(Collectors.toList()));
            patientsTable.setItems(patients);}
        catch (Exception e) {e.printStackTrace();showAlert("Error loading patient data.");
       }
        addViewReportsButton();

    }

    @FXML
    private void handleGoBack() {
        try {
            navigateToScene(goback, "/fxml/admin.fxml", "Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back to admin dashboard.");
        }
    }

    private void addViewReportsButton() {
        // Add buttons to the View Reports column
        viewReportsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View");

            {
                viewButton.setOnAction(event -> {
                    // Get the frontend Patient object from the current row of the TableView
                    Patient frontendPatient = getTableView().getItems().get(getIndex());

                    // Convert to backend Patient entity
                    com.remote_vitals.entities.Patient backendPatient = convertToBackendEntity(frontendPatient);

                    // Pass the backend Patient to the handleViewReports method
                    handleViewReports(backendPatient);
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
        });
    }

    private void handleViewReports(com.remote_vitals.entities.Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_view_vitals.fxml"));
            Parent root = loader.load();

            // Pass the appointment details to the next controller
            AdminViewVitalsController controller = loader.getController();
            controller.viewVitals(patient);

            // Navigate to the "View Vitals" page
            Stage stage = (Stage) patientsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("View Vitals");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Patient convertToModel(com.remote_vitals.entities.Patient entity) {
        return new Patient(
                entity.getId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getGender().toString(),
            entity.getPhoneNumber(),
            entity.getEmail(),
            entity.getMedicalHistory() // Assuming condition is stored in medicalHistory
        );
    }

private com.remote_vitals.entities.Patient convertToBackendEntity(Patient frontendPatient) {
    com.remote_vitals.entities.Patient backendPatient = new com.remote_vitals.entities.Patient();
    backendPatient.setFirstName(frontendPatient.getFirstName());
    backendPatient.setLastName(frontendPatient.getLastName());
    backendPatient.setId(frontendPatient.getId());

    backendPatient.setPhoneNumber(frontendPatient.getPhoneNumber());
    backendPatient.setEmail(frontendPatient.getEmail());

    return backendPatient;
}
}