package ui.patient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.*;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;
import javafx.scene.control.ListCell;

public class PatientEHRController {

    @FXML private ListView<EHR> ehrList;

    @FXML
    public void initialize() {

        Patient p = (Patient) DashboardNavigator.getCurrentUser();

        ehrList.setItems(FXCollections.observableArrayList(
                ServiceRegistry.ehrManager.getRecordsForPatient(p.getId())
        ));

        ehrList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(EHR record, boolean empty) {
                super.updateItem(record, empty);

                if (empty || record == null) {
                    setText(null);
                    return;
                }

                // SAFE provider lookup
                User provider = ServiceRegistry.userDb.findById(record.getProviderId());
                String providerName = (provider == null ? "Unknown Provider" : provider.getName());

                setText(
                        "Date: " + record.getDate() +
                                "\nProvider: " + providerName +
                                "\nDiagnosis: " + record.getDiagnosis() +
                                "\nTreatment: " + record.getTreatment() +
                                "\nNotes: " + record.getNotes() +
                                "\n-----------------------------"
                );
            }
        });
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("patient/PatientDashboard.fxml");
    }
}
