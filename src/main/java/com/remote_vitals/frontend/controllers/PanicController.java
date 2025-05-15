package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.EmailService;
import com.remote_vitals.backend.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import com.remote_vitals.entities.Patient;
import com.remote_vitals.entities.User;

import java.util.Optional;

public class PanicController extends BaseController {

    @FXML
    private Button panicButton;

    @FXML
    private Label statusLabel;
    @FXML private Button goBackButton;

    @FXML
    private void handlePanic() {
        
            Optional<User> userOptional = StaticDataClass.context.getBean(UserService.class).getCurrentUser();
if (userOptional.isPresent() && userOptional.get() instanceof Patient) {
    String nextOfKinEmail = ((Patient) userOptional.get()).getNextOfKinEmail();
    StaticDataClass.context.getBean(EmailService.class).sendEmail(nextOfKinEmail,
            "Panic Alert",
            "This is a panic alert from " + userOptional.get().getFirstName() + " " + userOptional.get().getLastName() + ".\n" +
                    "Please check on them immediately.",null);

    showAlert("Panic alert sent to RELATIVE email.");

} else {
    showAlert("your RELATIVE  email is not set.");
}}

    @FXML
    private void handleGoBack() {
        try {
            navigateToScene(goBackButton, "/fxml/patient.fxml", "Patient  Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back to Patient dashboard.");
        }
    }


}