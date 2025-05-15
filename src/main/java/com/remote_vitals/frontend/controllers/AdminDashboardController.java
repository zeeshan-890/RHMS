package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.repositories.AdminUpdateDto;
import com.remote_vitals.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminDashboardController extends BaseController {

    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label genderLabel;

    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button allPatient;
    @FXML private Button allDoctors;
    @FXML private Button addDoctors;
    @FXML private Button goback;
    @FXML private Button logout;


    @FXML
    public void initialize() {
        loadProfileView();
    }

    private void loadProfileView() {
        try {
            UserService userService = StaticDataClass.context.getBean(UserService.class);
            User admin = userService.getCurrentUser().orElse(null);

            if (admin != null) {
                // Set non-editable fields
                firstNameLabel.setText(admin.getFirstName());
                lastNameLabel.setText(admin.getLastName());
                genderLabel.setText(admin.getGender().toString());

                // Set editable fields
                phoneNumberField.setText(admin.getPhoneNumber());
                emailField.setText(admin.getEmail());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading admin profile.");
        }
    }

    @FXML
    private void handleUpdateAdminInfo() {
        try {
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            AdminUpdateDto updateDto = new AdminUpdateDto(
                    phoneNumber,
                    null,
                    email,
                    null,
                    password
            );

            StaticDataClass.context.getBean(UserService.class).updateUser(updateDto);
            showAlert("Admin information updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error updating admin info: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        try {
            navigateToScene(null, "/fxml/login.fxml", "Login");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error logging out.");
        }
    }

    @FXML
    private void handleAllPatients() {
        try {
            navigateToScene(allPatient, "/fxml/view_patients.fxml", "All Patients");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading patient list.");
        }
    }
    @FXML
    private void handleAllDoctors() {
        try {
            navigateToScene(allDoctors, "/fxml/view_doctors.fxml", "All Patients");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading Doctor list.");
        }
    }
    @FXML
    private void handleAddDoctors() {
        try {
            navigateToScene(addDoctors, "/fxml/registerDoctor.fxml", "Doctor Sign Up");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading patient list.");
        }
    }


    @FXML
    private void handleViewReports() {
        try {
            navigateToScene(null, "/fxml/view_reports.fxml", "Reports");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading Reports screen.");
        }
    }

    @FXML
    private void handleSystemSettings() {
        try {
            navigateToScene(null, "/fxml/system_settings.fxml", "System Settings");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading System Settings.");
        }
    }



}
