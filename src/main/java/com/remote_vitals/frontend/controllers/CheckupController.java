package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.Appointment;
import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.repositories.AppointmentRepository;
import com.remote_vitals.backend.services.CheckUpService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class CheckupController extends BaseController{

    @FXML
    private TextArea feedbackArea;

    @FXML
    private TextArea prescriptionArea;

    @FXML
    private Button submitButton;

    private Appointment currentAppointment; // Hold the clicked appointment for checkup

    private final AppointmentRepository appointmentRepository =
            StaticDataClass.context.getBean(AppointmentRepository.class); // Inject the repository/service

    /**
     * This method is called to pass the Appointment data to the controller.
     */
    public void setAppointmentDetails(Appointment appointment) {
        this.currentAppointment = appointment;


    }

    @FXML
    public void initialize() {
        // Attach a listener to handle the submit button click action
        submitButton.setOnAction(event -> handleSubmit());
    }

    private void handleSubmit() {
        if (currentAppointment == null) {
            System.err.println("No appointment selected for checkup.");
            return;
        }

        try {
            // Get input data from the feedback and prescription text areas
            String feedback = feedbackArea.getText().trim();
            String prescription = prescriptionArea.getText().trim();




            Integer patientId = currentAppointment.getPatient().getId();
            Integer doctorId = currentAppointment.getDoctor().getId();
           StaticDataClass.context.getBean(CheckUpService.class).submitCheckUp(patientId, doctorId, feedback, prescription);
            // Feedback to the user about successful submission
            showAlert("Success  " +
                    "Checkup details have been saved successfully.");
            navigateToScene(submitButton, "/fxml/mypatients.fxml", "My Patients");
        } catch (Exception e) {
            e.printStackTrace();
            // Failed to save
            showAlert("Failed to save checkup details: " + e.getMessage());
        }
    }


}