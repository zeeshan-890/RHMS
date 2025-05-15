package com.remote_vitals.frontend.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Utility class for handling navigation between different screens in the application.
 * This class provides methods to switch scenes, show dialogs, and handle navigation errors.
 */
public class NavigationUtil {

    /**
     * Navigates to a new screen by loading the specified FXML file.
     * Replaces the current scene with the new one while preserving window dimensions.
     *
     * @param currentStage The current stage to change the scene on
     * @param fxmlPath The path to the FXML file to load
     * @param title The title for the new window
     * @return The controller instance for the loaded FXML
     */
    public static <T> T navigateToScreen(Stage currentStage, String fxmlPath, String title) {
        try {
            // Store current dimensions
            double width = currentStage.getWidth();
            double height = currentStage.getHeight();
            boolean isMaximized = currentStage.isMaximized();
            
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Make the scene responsive
            makeResponsive(root, scene);
            
            currentStage.setTitle(title);
            currentStage.setScene(scene);
            
            // Restore dimensions if not the first load
            if (width > 0 && height > 0) {
                currentStage.setWidth(width);
                currentStage.setHeight(height);
                currentStage.setMaximized(isMaximized);
            } else {
                // Set default dimensions for first load
                currentStage.setWidth(1200);
                currentStage.setHeight(700);
                currentStage.setMinWidth(800);
                currentStage.setMinHeight(600);
            }
            
            currentStage.show();
            
            return loader.getController();
        } catch (IOException e) {
            showErrorAlert("Navigation Error", "Could not navigate to " + fxmlPath, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Creates a new window (stage) with the specified FXML content.
     * Uses standard dimensions for consistency across the application.
     *
     * @param fxmlPath The path to the FXML file to load
     * @param title The title for the new window
     * @return The controller instance for the loaded FXML
     */
    public static <T> T openInNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Make the scene responsive
            makeResponsive(root, scene);
            
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            
            // Set standard dimensions for new windows
            // These values can be adjusted as needed for the application
            stage.setWidth(1200);
            stage.setHeight(700);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            
            stage.show();
            
            return loader.getController();
        } catch (IOException e) {
            showErrorAlert("Navigation Error", "Could not open new window for " + fxmlPath, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Shows an error alert with the specified information.
     *
     * @param title The title of the alert
     * @param header The header text for the alert
     * @param content The content text for the alert
     */
    public static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Shows an information alert with the specified information.
     *
     * @param title The title of the alert
     * @param header The header text for the alert
     * @param content The content text for the alert
     */
    public static void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Makes a parent layout responsive to window resizing.
     * Call this method from a controller's initialize method to enable
     * responsive behavior for the root layout.
     * 
     * @param root The root layout container (e.g., BorderPane, VBox)
     * @param scene The scene containing the layout
     */
    public static void makeResponsive(Parent root, Scene scene) {
        if (root == null || scene == null) {
            return;
        }
        
        // Set minimum dimensions to ensure usability
        Stage stage = (Stage) scene.getWindow();
        if (stage != null) {
            stage.setMinWidth(800);
            stage.setMinHeight(600);
        }
        
        // Make sure the root layout binds to the scene dimensions
        if (root instanceof javafx.scene.layout.Region) {
            javafx.scene.layout.Region region = (javafx.scene.layout.Region) root;
            region.prefWidthProperty().bind(scene.widthProperty());
            region.prefHeightProperty().bind(scene.heightProperty());
        }
    }
} 