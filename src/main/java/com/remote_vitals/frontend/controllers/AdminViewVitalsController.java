package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.VitalReportTxtService;
import com.remote_vitals.entities.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class AdminViewVitalsController extends BaseController {

    @FXML
    private TextArea vitalsTextArea;
    @FXML private Button goBack;

    @FXML
    public void initialize() {
        // Initialize the text area
    }



    public void viewVitals(Patient patient) {
        try {
            Integer patientId = patient.getId();

            String text = StaticDataClass.context.getBean(VitalReportTxtService.class).getVitalReportData(patientId);
            vitalsTextArea.setText(text);
            System.out.println("Patient ID: " + patientId);
            System.out.println("Vitals: " + text);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoBak() {
        try {
            // Navigate back to the previous view
            navigateToScene(goBack, "/fxml/view_patients.fxml", "My Patients");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back.");
        }
    }
}