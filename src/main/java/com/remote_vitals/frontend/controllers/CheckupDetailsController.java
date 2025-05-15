package com.remote_vitals.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.stage.Stage;
import com.remote_vitals.frontend.controllers.CheckupsController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckupDetailsController extends BaseController {

    @FXML
    private Label doctorLabel;

    @FXML
    private Label prescriptionLabel;

    @FXML
    private TextArea feedbackArea;

    @FXML
    private Label timeLabel;

    @FXML
    private Button goBackButton;

    @FXML
    public void initialize() {
        CheckupsController.Checkup checkup = new CheckupsController.Checkup();
        setCheckupDetails(checkup);
        // Initialization logic if needed
        // The checkup details will be set by the setCheckupDetails method
    }

    // private void populateDetails(String doctor, String prescription, String feedback, LocalDateTime time) {
    //     doctorLabel.setText(doctor);
    //     prescriptionLabel.setText(prescription);
    //     feedbackArea.setText(feedback);
    //     timeLabel.setText(time.format(DateTimeFormatter.ofPattern("hh:mm a, MMMM dd, yyyy"))); // e.g., 05:00 PM, October 28, 2023
    // }

    @FXML
    private void handleGoBack() {
        try {
            // Navigate back to the previous scene (e.g., Patient Dashboard or Checkup List page)
            Node sourceNode = goBackButton;
            navigateToScene(sourceNode, "/fxml/checkups.fxml", "Patient Checkups");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back to the previous page.");
        }
    }

    public void setCheckupDetails(CheckupsController.Checkup checkup) {
        doctorLabel.setText(checkup.getDoctorName());
        // Check if prescription and feedback are available in the Checkup model
        // If not, handle gracefully
        String prescription = checkup.getPrescription(); // Default message
        String feedback = checkup.getFeedback(); // Default message
        // Uncomment and update if these fields are added to the Checkup model
        // prescription = checkup.getPrescription();
        // feedback = checkup.getFeedback();
        prescriptionLabel.setText(prescription);
        feedbackArea.setText(feedback);
        timeLabel.setText(checkup.getCheckupDate());
    }
}