package com.remote_vitals.frontend.controllers;

import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.backend.services.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import com.remote_vitals.frontend.models.Doctor;

import java.util.stream.Collectors;

public class ViewDoctorsController extends BaseController {

    @FXML
    private TableView<Doctor> doctorsTable;

    @FXML
    private TableColumn<Doctor, String> firstnameColumn;
    @FXML
    private TableColumn<Doctor, String> lastnameColumn;
    @FXML
    private TableColumn<Doctor, String> genderColumn;
    @FXML
    private TableColumn<Doctor, String> phoneNumberColumn;
    @FXML
    private TableColumn<Doctor, String> emailColumn;
    @FXML
    private TableColumn<Doctor, String> qualificationColumn;
    @FXML
    private Button goback;

    @FXML
    public void initialize() {
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        qualificationColumn.setCellValueFactory(new PropertyValueFactory<>("qualification"));

        // Load doctor data

        new Thread(() -> {
            try {
                UserService userService = StaticDataClass.context.getBean(UserService.class);
                ObservableList<Doctor> doctors = FXCollections.observableArrayList(
                        userService.getAllDoctors().stream().map(this::convertToModel).collect(Collectors.toList())
                );
                Platform.runLater(() -> doctorsTable.setItems(doctors));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> showAlert("Error loading patient data."));
            }
        }).start();
    }

    @FXML
    private void handleGoBack() {
        try {
            navigateToScene(goback, "/fxml/admin.fxml", "Admin Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error navigating back to admin dashboard.");
        }
    }
    private Doctor convertToModel(com.remote_vitals.entities.Doctor entity) {
        return new Doctor(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender().toString(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getQualificationString() // Assuming condition is stored in medicalHistory
        );
    }

}


