package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.AppointmentService;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.entities.Doctor;
import com.remote_vitals.repositories.DoctorRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentsController extends BaseController {

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, String> dateCol;

    @FXML
    private TableColumn<Appointment, String> doctorCol;

    @FXML
    private TableColumn<Appointment, String> statusCol;
    
    @FXML
    private TableColumn<Appointment, String> linkCol;

    @FXML
    private ComboBox<DoctorItem> doctorDropdown;
    @FXML  private  Button goBackButton;

    @FXML
    private DatePicker appointmentDate;
    
    @FXML
    private TextField meetingLinkField;

    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private DoctorRepository doctorRepository;
    private AppointmentService appointmentService;
    private UserService userService;

    @FXML
    public void initialize() {
        doctorRepository = StaticDataClass.context.getBean(DoctorRepository.class);
        appointmentService = StaticDataClass.context.getBean(AppointmentService.class);
        userService = StaticDataClass.context.getBean(UserService.class);

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        doctorCol.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        linkCol.setCellValueFactory(new PropertyValueFactory<>("link"));

        appointmentTable.setItems(appointments);

        loadDoctors();
        loadAppointments();

        appointmentDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    }

    private void loadDoctors() {
        try {
            List<Doctor> doctors = userService.getAllDoctors();
            List<DoctorItem> doctorItems = doctors.stream()
                    .map(doctor -> new DoctorItem(
                            doctor.getId(),
                            "Dr. " + doctor.getFirstName() + " " + doctor.getLastName()))
                    .collect(Collectors.toList());

            doctorDropdown.setItems(FXCollections.observableArrayList(doctorItems));
            doctorDropdown.setPromptText("Select Doctor");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to load doctors list");
        }
    }

    private void loadAppointments() {
        appointments.clear();
        try {
            List<com.remote_vitals.entities.Appointment> serviceAppointments =
                    appointmentService.getAllPatientAppointments();

            if (serviceAppointments != null && !serviceAppointments.isEmpty()) {
                for (com.remote_vitals.entities.Appointment appt : serviceAppointments) {
                    addAppointmentToTable(appt);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to load appointments");
        }
    }

    private void addAppointmentToTable(com.remote_vitals.entities.Appointment appt) {
        Doctor doc = appt.getDoctor();
        String doctorName = "Dr. " + doc.getFirstName() + " " + doc.getLastName();
        String dateStr = appt.getSchedule().getStartingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String meetingLink = appt.getLinkForRoom() != null ? appt.getLinkForRoom() : "";
        Appointment appointment = new Appointment(dateStr, doctorName, appt.getStatus().toString(), meetingLink);
        appointments.add(appointment);
    }

    @FXML
    private void handleBookAppointment() {
        try {
            DoctorItem selectedDoctor = doctorDropdown.getValue();
            LocalDate selectedDate = appointmentDate.getValue();
            String meetingLink = meetingLinkField.getText();

            if (selectedDoctor == null || selectedDate == null) {
                showAlert("Please select both doctor and date.");
                return;
            }

            LocalDateTime appointmentDateTime = LocalDateTime.of(selectedDate, LocalTime.of(0, 0));
            String timestamp = appointmentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

            try {
                AppointmentService service = StaticDataClass.context.getBean(AppointmentService.class);

                // Request appointment
                service.requestAppointment(selectedDoctor.getId(), LocalDateTime.parse(timestamp));

                // Fetch all appointments again and find the newly added one
                List<com.remote_vitals.entities.Appointment> updatedList = service.getAllPatientAppointments();

                com.remote_vitals.entities.Appointment newlyAdded = updatedList.stream()
                        .filter(a -> a.getDoctor().getId().equals(selectedDoctor.getId()) &&
                                a.getSchedule().getStartingTime().equals(LocalDateTime.parse(timestamp)))
                        .findFirst()
                        .orElse(null);

                if (newlyAdded != null) {
                    // Set the meeting link if provided
                    if (meetingLink != null && !meetingLink.trim().isEmpty()) {
                        Optional<com.remote_vitals.entities.Appointment> updatedAppointment =
                                service.setAppointmentLink(newlyAdded.getId(), meetingLink);
                        
                        if (updatedAppointment.isPresent()) {
                            addAppointmentToTable(updatedAppointment.get());
                        } else {
                            addAppointmentToTable(newlyAdded);
                        }
                    } else {
                        addAppointmentToTable(newlyAdded);
                    }
                }

                // Clear selections
                doctorDropdown.setValue(null);
                appointmentDate.setValue(null);
                meetingLinkField.clear();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error booking appointment: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error: " + e.getMessage());
        }
    }
    @FXML
    private void handleGoBack() {
        try {
            // Navigate back to the previous view
            navigateToScene(goBackButton, "/fxml/patient.fxml", "My Patients");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back.");
        }}

    // Helper class for doctor dropdown items
    static class DoctorItem {
        private final Integer id;
        private final String name;

        public DoctorItem(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Data model class for appointment table
    public static class Appointment {
        private final StringProperty date;
        private final StringProperty doctor;
        private final StringProperty status;
        private final StringProperty link;

        public Appointment(String date, String doctor, String status, String link) {
            this.date = new SimpleStringProperty(date);
            this.doctor = new SimpleStringProperty(doctor);
            this.status = new SimpleStringProperty(status);
            this.link = new SimpleStringProperty(link);
        }

        public String getDate() {
            return date.get();
        }

        public String getDoctor() {
            return doctor.get();
        }

        public String getStatus() {
            return status.get();
        }
        
        public String getLink() {
            return link.get();
        }

        public StringProperty dateProperty() {
            return date;
        }

        public StringProperty doctorProperty() {
            return doctor;
        }}}