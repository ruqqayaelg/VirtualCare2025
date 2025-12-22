package ui.provider;

import javafx.fxml.FXML;
import ui.common.DashboardNavigator;

public class ProviderDashboardController {

    @FXML
    private void goToAppointments() {
        DashboardNavigator.navigateTo("provider/ProviderAppointments.fxml");
    }

    @FXML
    private void goToPatients() {
        DashboardNavigator.navigateTo("provider/ProviderPatients.fxml");
    }

    @FXML
    private void goToAvailability() {
        DashboardNavigator.navigateTo("provider/ProviderAvailability.fxml");
    }

    @FXML
    private void goToEHR() {
        DashboardNavigator.navigateTo("provider/ProviderEHR.fxml");
    }

    @FXML
    private void goToPrescriptions() {
        DashboardNavigator.navigateTo("provider/ProviderPrescriptions.fxml");
    }

    @FXML
    private void goToFeedback() {
        DashboardNavigator.navigateTo("provider/ProviderFeedback.fxml");
    }

    // ⭐ NEW: Consultation History
    @FXML
    private void goToHistory() {
        DashboardNavigator.navigateTo("provider/ProviderConsultationHistory.fxml");
    }

    @FXML
    private void logout() {
        DashboardNavigator.logout();
    }
}
