package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.repositories.DoctorUpdateDto;
import com.remote_vitals.entities.Doctor;
import com.remote_vitals.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DoctorDashboardController extends BaseController {

    // Labels for non-editable personal info
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label genderLabel;

    // Editable fields
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextArea qualificationsArea;
    @FXML private Button patient;
    @FXML private Button logout;
    @FXML private Button appoint;

    @FXML
    public void initialize() {
        loadProfileView();
    }

    private void loadProfileView() {
        try {
            UserService userService = StaticDataClass.context.getBean(UserService.class);
            User user = userService.getCurrentUser().orElse(null);

            if (user instanceof Doctor doctor) {
                // Set personal info
                firstNameLabel.setText(doctor.getFirstName());
                lastNameLabel.setText(doctor.getLastName());
                genderLabel.setText(doctor.getGender().toString());

                // Set editable fields
                phoneNumberField.setText(doctor.getPhoneNumber());
                emailField.setText(doctor.getEmail());
                qualificationsArea.setText(doctor.getQualificationString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading doctor profile.");
        }
    }

    @FXML
    private void handleUpdateDoctorInfo() {
        try {
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String qualifications = qualificationsArea.getText();

            DoctorUpdateDto updateDto = new DoctorUpdateDto(phoneNumber, null, email, null, password, qualifications, null);

            StaticDataClass.context.getBean(UserService.class).updateUser(updateDto);

            showAlert("Doctor information updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error updating doctor info: " + e.getMessage());
        }
    }

    @FXML
    private void handleViewPatients() {
        try {
            navigateToScene(patient, "/fxml/mypatients.fxml", "View Patients");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading patient list.");
        }
    }

    @FXML
    private void handleAppointments() {
        try {
            navigateToScene(appoint, "/fxml/doctor_appointments.fxml", "Appointments");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading appointments.");
        }
    }



    @FXML
    private void handleLogout() {
        try {
            navigateToScene(logout, "/fxml/login.fxml", "Login");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error logging out.");
        }
    }


}
