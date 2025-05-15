//package RPMS.controllers.patient;
//
//import ChatAndVideoConsultation.ChatClient;
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//
//public class ChatController {
//
//    @FXML
//    private ScrollPane chatScrollPane;
//
//    @FXML
//    private VBox chatBox;
//
//    @FXML
//    private TextField messageField;
//
//    @FXML
//    private Button sendButton;
//
//    private ChatClient chatClient;
//
//    // Temporary hardcoded IDs for testing
//    private final String patientId = "patient123";
//    private final String doctorId = "doctor456";
//    private final String pairId = patientId + "_" + doctorId;
//
//    @FXML
//    public void initialize() {
//        // Start client and connect to ChatServer
//        chatClient = new ChatClient(patientId, doctorId, pairId);
//        chatClient.connect(this::displayMessage);
//    }
//
//    @FXML
//    private void handleSend() {
//        String message = messageField.getText().trim();
//        if (!message.isEmpty()) {
//            chatClient.sendMessage(message);
//            displayMessage("You: " + message);
//            messageField.clear();
//        }
//    }
//
//    private void displayMessage(String message) {
//        Platform.runLater(() -> {
//            Label messageLabel = new Label(message);
//            messageLabel.setWrapText(true);
//            messageLabel.setStyle("-fx-background-color: #FEFAE0; -fx-padding: 8 12 8 12; -fx-border-color: #D4A373; -fx-background-radius: 6;");
//            chatBox.getChildren().add(messageLabel);
//
//            // Auto-scroll to bottom
//            chatScrollPane.layout();
//            chatScrollPane.setVvalue(1.0);
//        });
//    }
//
//    public void shutdown() {
//        if (chatClient != null) {
//            chatClient.disconnect();
//