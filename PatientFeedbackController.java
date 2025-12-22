package ui.patient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Feedback;
import model.Patient;
import service.FeedbackProcessor;
import ui.common.DashboardNavigator;

public class PatientFeedbackController {

    @FXML private TextArea feedbackArea;
    @FXML private Label messageLabel;

    @FXML
    private void submitFeedback() {

        Patient patient =
                (Patient) DashboardNavigator.getCurrentUser();

        if (patient == null || feedbackArea.getText().isBlank()) {
            messageLabel.setText("Feedback cannot be empty.");
            return;
        }

        FeedbackProcessor.submitFeedback(
                patient,
                feedbackArea.getText().trim()
        );

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Feedback submitted successfully!");
        feedbackArea.clear();
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("patient/PatientDashboard.fxml");
    }
}
