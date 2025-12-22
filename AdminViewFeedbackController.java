package ui.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import ui.common.DashboardNavigator;

public class AdminViewFeedbackController {

    @FXML private TableView<?> feedbackTable;
    @FXML private TableColumn<?, ?> patientColumn;
    @FXML private TableColumn<?, ?> providerColumn;
    @FXML private TableColumn<?, ?> ratingColumn;
    @FXML private TableColumn<?, ?> commentColumn;

    @FXML
    public void initialize() {
        // Load ALL feedback records from feedback database
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("admin/AdminDashboard.fxml");
    }
}
