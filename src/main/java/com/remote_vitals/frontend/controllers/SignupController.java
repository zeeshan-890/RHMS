package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.SignUpService;
import com.remote_vitals.entities.Patient;
import com.remote_vitals.repositories.Gender;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.util.Locale;

public class SignupController extends BaseController{

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button login;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private ComboBox<String> bloodGroupCombo;



    @FXML
    private void initialize() {
        // Initialize gender options
        genderComboBox.getItems().addAll("Male", "Female", "Other", "Prefer not to say");
    }

    @FXML
    private void handleSignup(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gend = genderComboBox.getValue();
        LocalDate dateOfBirth = dobPicker.getValue();
        String bloodGroup = bloodGroupCombo.getValue();

        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate all fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || gend == null ||dateOfBirth==null || bloodGroup == null ||bloodGroup==null ||
                phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Please fill in all required fields.");
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match.");
            return;
        }

        // Validate email format
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Please enter a valid email address.");
            return;
        }

        // Validate phone number format (basic validation)
        if (!phoneNumber.matches("^[0-9]{10,15}$")) {
            showAlert("Please enter a valid phone number (10-15 digits).");
            return;
        }
        Gender gender;
        try {
            gender = Gender.valueOf(gend.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            showAlert("Invalid gender selected.");
            return;
        }


        // Create a new Patient object
        Patient patient = new Patient(
                firstName,
                lastName,
                gender,
                phoneNumber,
                email,
                password,
                null, // Medical history (can be added later)
                bloodGroup,// Blood group (optional)
                dateOfBirth, // Date of birth (optional)
                null  // Next of kin email (optional)
        );

        // Pass the Patient object to the SignUpService
        int result = StaticDataClass.context.getBean(SignUpService.class).signUp(patient);
        if (result == 0) {
            showAlert("Registration successful! Redirecting to login...");
            redirectToLogin(null);
        } else {
            showAlert("Registration failed. Email or phone number might already exist.");
        }
    }




    @FXML
    private void redirectToLogin(ActionEvent event) {
        try {
           navigateToScene(login, "/fxml/login.fxml", "Login Page");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading login screen.");
        }
    }



}