package ui.patient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.Bill;
import model.Patient;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class PatientBillsController {

    @FXML private TableView<Bill> billsTable;

    @FXML
    public void initialize() {

        Patient patient = (Patient) DashboardNavigator.getCurrentUser();

        billsTable.setItems(
                FXCollections.observableArrayList(
                        ServiceRegistry.billingDb.findAll().stream()
                                .filter(b -> b.getPatient() != null) // ✅ NULL SAFE
                                .filter(b -> patient.getId().equals(b.getPatient().getId()))
                                .toList()
                )
        );

        // ✅ STORE SELECTED BILL GLOBALLY
        billsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    DashboardNavigator.setSelectedBill(selected);
                });
    }

    @FXML
    private void payBill() {
        DashboardNavigator.navigateTo("ui/patient/BillingPayment.fxml");
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("ui/patient/PatientDashboard.fxml");
    }
}
