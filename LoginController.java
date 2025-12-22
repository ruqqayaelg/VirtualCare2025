package ui.common;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Administrator;
import model.HealthcareProvider;
import model.Patient;
import model.User;
import service.ServiceRegistry;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void login() {

        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        User user = ServiceRegistry.authenticationController.login(email, password);

        if (user == null) {
            messageLabel.setText("Invalid credentials.");
            return;
        }

        DashboardNavigator.setCurrentUser(user);

        if (user instanceof Patient) {
            DashboardNavigator.navigateTo("/patient/PatientDashboard.fxml");
        }
        else if (user instanceof HealthcareProvider) {
            DashboardNavigator.navigateTo("/provider/ProviderDashboard.fxml");
        }
        else if (user instanceof Administrator) {
            DashboardNavigator.navigateTo("/admin/AdminDashboard.fxml");
        }
    }

    @FXML
    private void goToRegister() {
        DashboardNavigator.navigateTo("/patient/PatientRegister.fxml");
    }

}
