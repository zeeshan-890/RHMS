package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.Appointment;
import com.remote_vitals.repositories.AppointmentStatus;
import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.AppointmentService;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class MyPatientsController extends BaseController {

    @FXML
    private TableView<Appointment> patientsTable;

    @FXML
    private TableColumn<Appointment, String> patientNameCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDateCol;

    @FXML
    private TableColumn<Appointment, Void> viewVitalsCol;

    @FXML
    private TableColumn<Appointment, Void> checkupCol;
    @FXML private Button goBackButton;

    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private AppointmentService appointmentService;

    @FXML
    public void initialize() {
        // Load the AppointmentService bean
        appointmentService = StaticDataClass.context.getBean(AppointmentService.class);

        // Set up the TableView columns
        patientNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getPatient().getFirstName() + " " +
                cellData.getValue().getPatient().getLastName()));
        appointmentDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getSchedule().getStartingTime().toString()));

        // Add View Vitals and Checkup actions
        setupViewVitalsColumn();
        setupCheckupColumn();

        // Populate the table with scheduled appointments
        loadScheduledAppointments();
    }

    private void loadScheduledAppointments() {
        appointments.clear();

        // Fetch and filter appointments with scheduled status
        List<Appointment> scheduledAppointments = appointmentService.getAllAppointments();
        scheduledAppointments = scheduledAppointments.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED)
                .collect(Collectors.toList());

        appointments.addAll(scheduledAppointments);
        patientsTable.setItems(appointments);
    }

    private void setupViewVitalsColumn() {
        viewVitalsCol.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View Vitals");

            {
                viewButton.setOnAction(event -> {
                    Appointment appointment = getTableView().getItems().get(getIndex());
                    if (appointment != null) {
                        navigateToViewVitals(appointment);
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
        });
    }

    private void setupCheckupColumn() {
        checkupCol.setCellFactory(param -> new TableCell<>() {
            private final Button checkupButton = new Button("Checkup");

            {
                checkupButton.setOnAction(event -> {
                    Appointment appointment = getTableView().getItems().get(getIndex());
                    if (appointment != null) {
                        navigateToCheckup(appointment);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(checkupButton);
                }
            }
        });
    }

    private void navigateToViewVitals(Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view_vitals.fxml"));
            Parent root = loader.load();

            // Pass the appointment details to the next controller
            ViewVitalsController controller = loader.getController();
            controller.setAppointmentDetails(appointment);

            // Navigate to the "View Vitals" page
            Stage stage = (Stage) patientsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("View Vitals");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToCheckup(Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/checkup.fxml"));
            Parent root = loader.load();

            // Pass the appointment details to the next controller
            CheckupController controller = loader.getController();
            controller.setAppointmentDetails(appointment);

            // Navigate to the "Checkup" page
            Stage stage = (Stage) patientsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Checkup");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    @FXML
    private void handleGo() {
        try {
         navigateToScene(goBackButton,"/fxml/doctor.fxml","Doctor Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}