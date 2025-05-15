package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.VitalReportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UploadViltalsController extends BaseController {

    // Input Fields

    @FXML private TextField temperatureField;
    @FXML private TextField heartRateField;
    @FXML private TextField rbcField;
    @FXML private TextField wbcField;
    @FXML private TextField plateletField;
    @FXML private TextField respiratoryRateField;
    @FXML private TextField systolicField;
    @FXML private TextField diastolicField;
    @FXML private TextField bloodVolumeField;
    @FXML private TextField haemoglobinField;
    @FXML private TextField heightField;
    @FXML private TextField weightField;

    // Buttons & UI elements
    @FXML private Button submitManualButton;
    @FXML private Button backButton;
    @FXML private Button selectFileButton;
    @FXML private Button uploadDataButton;

    @FXML private StackPane dropZone;
    @FXML private ImageView fileUploadIcon;

    private File selectedFile;

    @FXML
    private void initialize() {
        // Optional: Initialization logic here
    }

    @FXML
    private void handleManualSubmit(ActionEvent event) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        String temperature = temperatureField.getText();
        String heartRate = heartRateField.getText();
        String rbc = rbcField.getText();
        String wbc = wbcField.getText();
        String platelet = plateletField.getText();
        String respiratoryRate = respiratoryRateField.getText();
        String systolic = systolicField.getText();
        String diastolic = diastolicField.getText();
        String bloodVolume = bloodVolumeField.getText();
        String haemoglobin = haemoglobinField.getText();
        String height = heightField.getText();
        String weight = weightField.getText();

        String text =
                timestamp + "," + temperature + "," + heartRate + "," + rbc + "," + wbc + "," +
                        platelet + "," + respiratoryRate + "," + systolic + "," + diastolic + "," +
                        bloodVolume + "," + haemoglobin + "," + height + "," + weight;
try {
    StaticDataClass.context.getBean(VitalReportService.class).appendToCsvForCurrentUser(text);

    StaticDataClass.context.getBean(VitalReportService.class).readCsvForCurrentUser();
    System.out.println(StaticDataClass.context.getBean(VitalReportService.class).readCsvForCurrentUser());
} catch (Exception e) {showAlert("Error saving manual data: " + e.getMessage());
    }
        showAlert("Manual data submitted successfully.");
    }
    @FXML
    private void handleSelectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Vitals CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = getStageFromEvent(event);
        try {
            selectedFile = fileChooser.showOpenDialog(stage);
        } catch (Exception e) {
            showAlert("Unable to open file chooser.");
        }

        if (selectedFile != null) {
            showAlert("You selected: " + selectedFile.getName());

            // Ask user where to save the file
        }
    }




    // Alternative method that saves to a predefined path
    @FXML
    private void saveSelectedFileToPath(String destinationPath) {
        if (selectedFile == null) {
            showAlert("No file has been selected.");
            return;
        }

        try {
            // Get the destination path
            Path target = Paths.get(destinationPath, selectedFile.getName());

            // Create parent directories if they don't exist
            Files.createDirectories(target.getParent());

            // Copy the file
            Files.copy(selectedFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING);

            showAlert("File successfully saved to: " + target.toString());
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error saving file: " + e.getMessage());
        }
    }



@FXML
private void handleUploadData(ActionEvent event) {
    if (selectedFile == null) {
        showAlert("Please select a file to upload.");
        return;
    }

    try {
           saveSelectedFileToPath("new_csv");
           StaticDataClass.context.getBean(VitalReportService.class).appendFileToCsv("new_csv/vitals.csv");
           StaticDataClass.context.getBean(VitalReportService.class).readCsvForCurrentUser();
        System.out.println( StaticDataClass.context.getBean(VitalReportService.class).readCsvForCurrentUser());

        showAlert("File uploaded successfully: " + selectedFile.getName());
    } catch (Exception e) {
        showAlert("Error uploading file: " + e.getMessage());
    }
}

@FXML
private void handleBack() {
    try {
        navigateToScene(backButton, "/fxml/vitals.fxml", "Patient Dashboard");
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Error navigating back.");
    }
}

private Stage getStageFromEvent(ActionEvent event) {
    return (Stage) ((Button) event.getSource()).getScene().getWindow();
}
}
