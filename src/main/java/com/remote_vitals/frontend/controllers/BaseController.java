package com.remote_vitals.frontend.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    // Common methods and properties for all controllers can be defined here
    // For example, you can define a method to show alerts or navigate between scenes

   
    protected void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    protected void navigateToScene(Node sourceNode, String fxmlFile, String title) throws IOException {
        // Create the FXMLLoader with explicit location setting
        FXMLLoader loader = new FXMLLoader();
        // Make sure the resource path is correct and exists
        loader.setLocation(getClass().getResource(fxmlFile));
        
        // Check if the location is valid
        if (loader.getLocation() == null) {
            throw new IOException("FXML file not found: " + fxmlFile);
        }
        
        Parent root = loader.load();
        
        // Handle the case where sourceNode might be null
        Stage stage;
        if (sourceNode != null) {
            stage = (Stage) sourceNode.getScene().getWindow();
        } else {
            // Get the currently active stage - this is a fallback
            stage = (Stage) getCurrentStage();
        }
        
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    // Helper method to get the current stage when source node is null
    private Stage getCurrentStage() {
        // This returns the primary stage from the current application
        // If multiple stages exist, you might need a different approach
        return (Stage) javafx.stage.Window.getWindows().stream()
            .filter(window -> window instanceof Stage && window.isShowing())
            .findFirst()
            .orElse(null);
    }
}