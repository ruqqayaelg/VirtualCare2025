package ui.admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import model.HealthcareProvider;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class AdminViewProvidersController {

    @FXML private TableView<HealthcareProvider> providersTable;
    @FXML private TableColumn<HealthcareProvider, String> firstNameColumn;
    @FXML private TableColumn<HealthcareProvider, String> emailColumn;
    @FXML private TableColumn<HealthcareProvider, String> specialtyColumn;

    @FXML
    public void initialize() {

        firstNameColumn.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getName())
        );

        emailColumn.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEmail())
        );

        specialtyColumn.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getSpecialty().name())
        );

        providersTable.setItems(
                FXCollections.observableArrayList(
                        ServiceRegistry.userDb.getAllHealthcareProviders()
                )
        );
    }

    @FXML
    private void removeProvider() {
        HealthcareProvider selected =
                providersTable.getSelectionModel().getSelectedItem();

        if (selected == null) return;

        ServiceRegistry.userDb.removeUser(selected.getId());
        providersTable.getItems().remove(selected);
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("admin/AdminDashboard.fxml");
    }
}
