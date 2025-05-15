package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.repositories.PatientUpdateDto;
import com.remote_vitals.entities.Patient;
import com.remote_vitals.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class PatientDashboardController extends BaseController {

    @FXML
    private StackPane contentArea;

    // Labels for personal (non-editable) info
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label bloodGroupLabel;

    // Editable fields
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextArea medicalHistoryArea;
    @FXML
    private TextField nextOfKinEmailField;

    @FXML private Button appointment;
    @FXML private Button checkup;
    @FXML private Button panic;
    @FXML private Button call;
    @FXML private Button vital;
    @FXML private Button delete;
    @FXML private Button logout;

    @FXML
    public void initialize() {
        loadProfileView();
    }

    private void loadProfileView() {
        try {
            UserService userService = StaticDataClass.context.getBean(UserService.class);
            User user = userService.getCurrentUser().get();
            Patient patient = (Patient) user;

            // Set non-editable labels
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            genderLabel.setText(user.getGender().toString());
            dobLabel.setText(patient.getDateOfBirth().toString());
            bloodGroupLabel.setText(patient.getBloodGroup());

            // Set editable fields
            phoneNumberField.setText(user.getPhoneNumber());
            emailField.setText(user.getEmail());
            medicalHistoryArea.setText(patient.getMedicalHistory());
            nextOfKinEmailField.setText(patient.getNextOfKinEmail());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading profile information");
        }
    }

    @FXML
    private void handleVitals() {
        try {
            navigateToScene(vital, "/fxml/vitals.fxml", "Vitals");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading vitals view");
        }
    }

    @FXML
    private void handleAppointments() {
        try {
            navigateToScene(appointment, "/fxml/appointments.fxml", "Appointments");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading appointments view");
        }
    }

    @FXML
    private void handleCheckup() {
        try {
            navigateToScene(checkup, "/fxml/checkups.fxml", "Feedback");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading checkup view");
        }
    }

    @FXML
    private void handlePanic() {
        try {
            navigateToScene(panic, "/fxml/panic.fxml", "Panic Alert");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading panic alert view");
        }
    }


    @FXML
    private void handleLogout() {
        try {
            navigateToScene(logout, "/fxml/login.fxml", "Login");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error logging out....");
        }
    }

    @FXML
    private void handleUpdateMedicalInfo() {
        try {
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String medicalHistory = medicalHistoryArea.getText();
            String nextOfKinEmail = nextOfKinEmailField.getText();

            PatientUpdateDto updateDto = new PatientUpdateDto(
                    phoneNumber,
                    null,
                    email,
                    null,
                    password,
                    medicalHistory,
                    nextOfKinEmail
            );

            StaticDataClass.context.getBean(UserService.class).updateUser(updateDto);

            showAlert("Medical information updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error updating information: " + e.getMessage());
        }
    }
}
   
