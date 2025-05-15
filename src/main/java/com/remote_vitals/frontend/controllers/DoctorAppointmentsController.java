package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.Appointment;
import com.remote_vitals.repositories.AppointmentStatus;
import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.AppointmentService;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.entities.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class DoctorAppointmentsController extends BaseController {

    @FXML
    private TableView<DoctorAppointment> appointmentTable;

    @FXML
    private TableColumn<DoctorAppointment, String> dateCol;

    @FXML
    private TableColumn<DoctorAppointment, String> patientCol;

    @FXML
    private TableColumn<DoctorAppointment, String> statusCol;

    @FXML
    private TableColumn<DoctorAppointment, String> linkCol;

    @FXML
    private TableColumn<DoctorAppointment, Void> linkActionCol;

    @FXML
    private TableColumn<DoctorAppointment, Void> acceptCol;

    @FXML
    private TableColumn<DoctorAppointment, Void> rejectCol;
    @FXML
    private Button goBack;

    private final ObservableList<DoctorAppointment> appointments = FXCollections.observableArrayList();
    private AppointmentService appointmentService;
    private UserService userService;
    // private final Button approveBtn = new Button("Approve");
    // private final Button rejectBtn = new Button("Reject");
    // private final HBox container = new HBox(10, approveBtn, rejectBtn);

    @FXML
    public void initialize() {
        appointmentService = StaticDataClass.context.getBean(AppointmentService.class);
        userService = StaticDataClass.context.getBean(UserService.class);

        // Set up the basic columns
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patient"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        linkCol.setCellValueFactory(new PropertyValueFactory<>("link"));

        // Set up the link action column
        setupLinkActionColumn();

        // Set up the accept and reject columns
        setupAcceptColumn();
        setupRejectColumn();

        // Set items and load data
        appointmentTable.setItems(appointments);
        loadAppointments();
    }

    private void setupLinkActionColumn() {
        linkActionCol.setCellFactory(param -> new TableCell<>() {
            private final Button updateLinkButton = new Button("Update Link");
            private final TextField linkField = new TextField();
            private final HBox container = new HBox(5, linkField, updateLinkButton);

            {
                container.setAlignment(Pos.CENTER_LEFT);
                linkField.setPrefWidth(150);
                updateLinkButton.setPrefWidth(140);

                updateLinkButton.setOnAction(event -> {
                    DoctorAppointment appointment = getTableView().getItems().get(getIndex());
                    String newLink = linkField.getText();
                    updateAppointmentLink(appointment, newLink);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    DoctorAppointment appointment = getTableView().getItems().get(getIndex());
                    linkField.setText(appointment.getLink());
                    setGraphic(container);
                }
            }
        });
    }

    private void setupAcceptColumn() {
        acceptCol.setCellFactory(column -> new TableCell<>() {
            private final Button acceptBtn = new Button("Accept");

            {
                acceptBtn.getStyleClass().add("primary-button");
                acceptBtn.setPrefWidth(140);
                acceptBtn.setOnAction(event -> {
                    DoctorAppointment appointment = getTableView().getItems().get(getIndex());
                    if (appointment != null) {
                        updateAppointmentStatus(appointment, AppointmentStatus.SCHEDULED);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    DoctorAppointment appointment = getTableView().getItems().get(getIndex());
                    if ("REQUESTED".equals(appointment.getStatus())) {
                        setGraphic(acceptBtn);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }
    @FXML
    private void scrollToBottom() {
        appointmentTable.scrollTo(appointmentTable.getItems().size() - 1);
    }
    private void setupRejectColumn() {
        rejectCol.setCellFactory(column -> new TableCell<>() {
            private final Button rejectBtn = new Button("Reject");

            {
                rejectBtn.getStyleClass().add("danger-button");
                rejectBtn.setPrefWidth(140);
                rejectBtn.setOnAction(event -> {
                    DoctorAppointment appointment = getTableView().getItems().get(getIndex());
                    if (appointment != null) {
                        updateAppointmentStatus(appointment, AppointmentStatus.REJECTED);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    DoctorAppointment appointment = getTableView().getItems().get(getIndex());
                    if ("REQUESTED".equals(appointment.getStatus())) {
                        setGraphic(rejectBtn);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
    }

    private void updateAppointmentStatus(DoctorAppointment appointment, AppointmentStatus status) {
        try {
            Optional<Appointment> updated = appointmentService.changeAppointmentStatus(
                appointment.getId(), status);

            if (updated.isPresent()) {
                appointment.setStatus(updated.get().getStatus().toString());
                appointmentTable.refresh();
                showAlert("Appointment " + (status == AppointmentStatus.SCHEDULED ? "approved" : "rejected") + " successfully");
            } else {
                showAlert("Failed to update appointment status");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error updating appointment status: " + ex.getMessage());
        }
    }

    private void loadAppointments() {
        appointments.clear();
        try {
            List<Appointment> doctorAppointments = appointmentService.getAllAppointments();

            if (doctorAppointments != null && !doctorAppointments.isEmpty()) {
                for (Appointment appt : doctorAppointments) {
                    addAppointmentToTable(appt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to load appointments");
        }
    }

    private void addAppointmentToTable(Appointment appt) {
        Patient patient = appt.getPatient();
        String patientName = patient.getFirstName() + " " + patient.getLastName();
        String dateStr = appt.getSchedule().getStartingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String meetingLink = appt.getLinkForRoom() != null ? appt.getLinkForRoom() : "";

        DoctorAppointment appointment = new DoctorAppointment(
            appt.getId(),
            dateStr,
            patientName,
            appt.getStatus().toString(),
            meetingLink
        );
        appointments.add(appointment);
    }

    private void updateAppointmentLink(DoctorAppointment appointment, String newLink) {
        if (newLink == null || newLink.trim().isEmpty()) {
            showAlert("Please enter a valid meeting link");
            return;
        }

        try {
            Optional<Appointment> updatedAppointment =
                appointmentService.setAppointmentLink(appointment.getId(), newLink);

            if (updatedAppointment.isPresent()) {
                // Update the appointment in the table
                appointment.setLink(newLink);
                appointmentTable.refresh();
                showAlert("Meeting link updated successfully");
            } else {
                showAlert("Failed to update meeting link");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error updating meeting link: " + e.getMessage());
        }
    }

    @FXML
    private void handleGoBack() {
        try {
            navigateToScene(goBack, "/fxml/doctor.fxml", "My Patients");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back to Patient dashboard.");
        }
    }

    // Data model class for appointment table
    public static class DoctorAppointment {
        private final Integer id;
        private final StringProperty date;
        private final StringProperty patient;
        private final StringProperty status;
        private final StringProperty link;

        public DoctorAppointment(Integer id, String date, String patient, String status, String link) {
            this.id = id;
            this.date = new SimpleStringProperty(date);
            this.patient = new SimpleStringProperty(patient);
            this.status = new SimpleStringProperty(status);
            this.link = new SimpleStringProperty(link);
        }

        public Integer getId() {
            return id;
        }

        public String getDate() {
            return date.get();
        }

        public String getPatient() {
            return patient.get();
        }

        public String getStatus() {
            return status.get();
        }

        public String getLink() {
            return link.get();
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

        public void setLink(String link) {
            this.link.set(link);
        }

        public StringProperty dateProperty() {
            return date;
        }

        public StringProperty patientProperty() {
            return patient;
        }

        public StringProperty statusProperty() {
            return status;
        }

        public StringProperty linkProperty() {
            return link;
        }
    }

}