package ui.admin;

import javafx.fxml.FXML;
import ui.common.DashboardNavigator;

public class AdminDashboardController {

    @FXML
    private void goToProviders() {
        DashboardNavigator.navigateTo("admin/AdminViewProviders.fxml");
    }

    @FXML
    private void goToRegisterProvider() {
        DashboardNavigator.navigateTo("admin/AdminRegisterProvider.fxml");
    }

    @FXML
    private void logout() {
        DashboardNavigator.logout(); // ✅ FIX
    }
}
