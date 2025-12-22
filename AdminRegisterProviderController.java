package ui.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.HealthcareProvider;
import model.enums.SpecialtyType;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

import java.util.UUID;

public class AdminRegisterProviderController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<SpecialtyType> specialtyBox;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        specialtyBox.getItems().setAll(SpecialtyType.values());
    }

    @FXML
    private void registerProvider() {

        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        SpecialtyType specialty = specialtyBox.getValue();

        // ✅ EMPTY FIELD CHECK
        if (name.isBlank() || email.isBlank() || password.isBlank() || specialty == null) {
            errorLabel.setText("All fields must be filled");
            return;
        }

        String providerId = ServiceRegistry.userDb.generateNextProviderId();

        HealthcareProvider provider = new HealthcareProvider(
                providerId,
                name,
                email,
                password,
                specialty
        );


        ServiceRegistry.userDb.add(provider);

        // ✅ SUCCESS MESSAGE
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Successfully registered");

        // Optional: clear fields after success
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        specialtyBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("admin/AdminDashboard.fxml");
    }
}
