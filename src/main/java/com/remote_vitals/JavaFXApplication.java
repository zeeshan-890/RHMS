package com.remote_vitals;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;

//import com.remote_vitals.backend.chat.entities.ChatRoom;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Application entry point
 * This class handles the initialization and startup of the JavaFX application.
 * It integrates Spring Boot with JavaFX and initializes the database with dummy data.
 */
public class JavaFXApplication extends Application {

    /** Spring application context for dependency injection */
    private ConfigurableApplicationContext context;

    /**
     * Initialization method called before the application starts
     * Sets up Spring context and initializes database with dummy data
     */
    @Override
    public void init() throws Exception {
        // Initialize Spring Boot context
      //context = org.springframework.boot.SpringApplication.run(RemoteVitalsApplication.class);
//
        // Set the database handler in BaseController first
//        BaseController.setContext(context);

        // Initialize appointment service
      



        System.out.println("**********************************************************");
    }
    
    /**
     * Main application start method
     * Loads the initial login screen
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Start with login screen for real-world application flow
        loadScreen(stage, "/fxml/login.fxml", "Login");
        
    }
    
    /**
     * Helper method to load a screen
     * Creates a new scene from FXML and sets it as the current stage
     * 
     * @param stage The stage to load the screen into
     * @param screenPath The path to the FXML file
     * @param title The title for the window
     * @throws IOException If the FXML file cannot be loaded
     */
    private void loadScreen(Stage stage, String screenPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource(screenPath));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}