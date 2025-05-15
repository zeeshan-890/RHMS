package com.remote_vitals.frontend.controllers;

import java.io.IOException;
import java.net.URL;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.LoginService;
import com.remote_vitals.entities.Admin;
import com.remote_vitals.entities.Doctor;
import com.remote_vitals.entities.Patient;
import com.remote_vitals.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController extends BaseController {

    private final LoginService loginService;

    public LoginController() {
        // Default constructor for FXML loader
        this.loginService = null; // This will be injected by Spring
    }

    // Inject LoginService using constructor-based injection
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @FXML
    private Button loginButton;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signup;

    @FXML
    public void initialize() {
        // Populate role options
        roleComboBox.getItems().addAll("Patient", "Doctor", "Admin");
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String selectedRole = roleComboBox.getValue();

        if (email == null || email.isBlank() || password == null || password.isBlank() || selectedRole == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            // Determine the user class based on the selected role
            // Determine the user class based on the selected role
            Class<? extends User> userClass = switch (selectedRole) {
                case "Patient" -> Patient.class;
                case "Doctor" -> Doctor.class;
                case "Admin" -> Admin.class;
                default -> throw new IllegalArgumentException("Unexpected role: " + selectedRole);
            };


            // Call the login service to validate credentials

            StaticDataClass.context.getBean(LoginService.class).login(email, password, userClass);

            // On successful login, navigate to the respective dashboard
            String dashboardPath = switch (selectedRole) {
                case "Patient" -> "/fxml/patient.fxml";
                case "Doctor" -> "/fxml/doctor.fxml";
                case "Admin" -> "/fxml/admin.fxml";
                default -> throw new IllegalStateException("Unexpected role: " + selectedRole);
            };

            try {
                navigateToScene(loginButton, dashboardPath, "Dashboard");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Unable to load Dashboard.");
            }
        } catch (RuntimeException e) {
            // Show error alert if login fails
            showAlert("Login Failed", e.getMessage());
        }
    }

    private void loadDashboard(String fxmlPath) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            System.out.println("Resolved: " + resource);
            if (resource == null) {
                throw new RuntimeException("FXML path not found: " + fxmlPath);
            }
            Parent dashboard = FXMLLoader.load(resource);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(dashboard));
        } catch (Exception e) {
            System.out.println(" Exception while loading " + fxmlPath);
            e.printStackTrace();
            showAlert("Error", "Failed to load dashboard: " + e.getMessage());
        }
    }

    @FXML

    private void handleSignup() {
        try {
            navigateToScene(signup, "/fxml/signup.fxml", "Signup Page");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load signup screen.");
        }
    }

    protected void showAlert(String header, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}