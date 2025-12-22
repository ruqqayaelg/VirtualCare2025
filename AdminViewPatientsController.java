package ui.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import model.Patient;
import ui.common.DashboardNavigator;

public class AdminViewPatientsController {

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, String> firstNameColumn;
    @FXML private TableColumn<Patient, String> lastNameColumn;
    @FXML private TableColumn<Patient, String> emailColumn;
    @FXML private TableColumn<Patient, String> phoneColumn;

    @FXML
    public void initialize() {
        // Load ALL patients from UserDatabase
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("admin/AdminDashboard.fxml");
    }
}
