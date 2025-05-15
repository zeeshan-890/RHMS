package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.UserService;
import com.remote_vitals.backend.services.VitalReportTxtService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Value;

/**
 * Controller for vitals.fxml (view + input in one).
 */
public class VitalsController extends BaseController {

    @FXML
    private TextArea vitalsReportArea;


    @FXML
    private TextField heartRateField;
    @FXML
    private TextField bpField;
    @FXML
    private TextField oxygenField;
    @FXML
    private TextField tempField;
    @FXML
    private Button uploadVital;
    @FXML private Button goBackButton;
    @FXML private Button download;


    @FXML
    public void initialize() {
        // Initialize the vitals report area
        vitalsReportArea.setEditable(false);
        vitalsReportArea.setWrapText(true);


       String text = StaticDataClass.context.getBean(VitalReportTxtService.class).getVitalReportData(StaticDataClass.context.getBean(UserService.class).getCurrentUser().get().getId());

        vitalsReportArea.setText(text);
    }
@Value("${path.download}")
        String downloadPath;
    @FXML
    private void handleUploadVital() {
        try {
            // Verify the button is properly initialized
            if (uploadVital == null) {
                throw new IllegalStateException("Upload Vital button is not initialized.");
            }
            navigateToScene(uploadVital, "/fxml/upload_vitals.fxml", "Upload Vitals");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading upload vitals view");
        }
    }
    @FXML
    private void handleGoBack() {
        try {
            // Navigate back to the previous view
            navigateToScene(goBackButton, "/fxml/patient.fxml", "Patient Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back.");

}
    }

    @FXML private void handleDownload() {
        try {
            // Verify the button is properly initialized
            String path = StaticDataClass.context.getBean(VitalReportTxtService.class).getDownloadPath();
            StaticDataClass.context.getBean(VitalReportTxtService.class).downloadVitalReportTxt(StaticDataClass.context.getBean(UserService.class).getCurrentUser().get().getId());
            showAlert("Vital Report Downloaded Successfully");
            ;
        }
             catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading download vitals view");
        }
    }

}