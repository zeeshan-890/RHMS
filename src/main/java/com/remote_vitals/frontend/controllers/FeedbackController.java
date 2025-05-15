package com.remote_vitals.frontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controller for the Feedback tab in Patient Dashboard.
 */
public class FeedbackController {

    @FXML private TableView<FeedbackEntry> feedbackTable;
    @FXML private TableColumn<FeedbackEntry, String> dateCol;
    @FXML private TableColumn<FeedbackEntry, String> messageCol;
    @FXML private TextArea feedbackInput;

    private final ObservableList<FeedbackEntry> feedbackList = FXCollections.observableArrayList();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        feedbackTable.setItems(feedbackList);

        // Load dummy initial data — replace with actual retrieval from DB or file
        feedbackList.add(new FeedbackEntry("2025-05-10", "The new vitals system works great."));
        feedbackList.add(new FeedbackEntry("2025-05-08", "Could we have dark mode?"));
    }

    @FXML
    private void handleSubmitFeedback() {
        String input = feedbackInput.getText().trim();
        if (!input.isEmpty()) {
            String today = LocalDate.now().format(formatter);
            FeedbackEntry entry = new FeedbackEntry(today, input);
            feedbackList.add(entry);

            // TODO: persist to database or file
            // FeedbackDatabase.save(entry);

            feedbackInput.clear();
        }
    }

    /** Inner class for table row model. */
    public static class FeedbackEntry {
        private final String date;
        private final String message;

        public FeedbackEntry(String date, String message) {
            this.date = date;
            this.message = message;
        }

        public String getDate() { return date; }

        public String getMessage() { return message; }
    }
}
