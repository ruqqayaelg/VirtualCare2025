package ui.provider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Feedback;
import model.HealthcareProvider;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class ProviderFeedbackController {

    @FXML private TableView<Feedback> feedbackTable;
    @FXML private TableColumn<Feedback, String> patientColumn;
    @FXML private TableColumn<Feedback, String> ratingColumn;
    @FXML private TableColumn<Feedback, String> commentsColumn;
    @FXML private Button backBtn;

    @FXML
    public void initialize() {

        HealthcareProvider provider = (HealthcareProvider) DashboardNavigator.getCurrentUser();

        patientColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPatient().getName()
                ));

        ratingColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getRating().name()
                ));

        commentsColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getComments()
                ));

        feedbackTable.setItems(FXCollections.observableArrayList(
                ServiceRegistry.feedbackProcessor.getAllFeedback()
                        .stream()
                        .filter(f -> f.getProvider().equals(provider))
                        .toList()
        ));
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("provider/ProviderDashboard.fxml");
    }
}
