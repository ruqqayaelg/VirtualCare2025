package ui.provider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.HealthcareProvider;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

import java.time.LocalDate;

public class ProviderAvailabilityController {

    @FXML private ListView<String> availabilityList;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> timePicker;

    @FXML private Button addSlotBtn;
    @FXML private Button removeSlotBtn;
    @FXML private Button backBtn;
    @FXML private Label messageLabel;

    private HealthcareProvider provider;

    @FXML
    public void initialize() {

        provider = (HealthcareProvider) DashboardNavigator.getCurrentUser();

        // Fill times into dropdown — you can add/remove times here
        timePicker.setItems(FXCollections.observableArrayList(
                "09:00", "10:00", "11:00", "13:00", "14:00", "15:00"
        ));

        refreshList();
    }

    private void refreshList() {
        availabilityList.setItems(FXCollections.observableArrayList(
                provider.getAvailabilitySlots().stream()
                        .map(slot -> slot.getDate() + "  " + slot.getTime())
                        .toList()
        ));
    }

    @FXML
    private void addSlot() {
        LocalDate date = datePicker.getValue();
        String time = timePicker.getValue();

        if (date == null || time == null) {
            messageLabel.setText("Please select both a date and time.");
            return;
        }

        if (provider.isAvailable(date, time)) {
            messageLabel.setText("This slot already exists.");
            return;
        }

        provider.addAvailability(date, time);
        ServiceRegistry.userDb.save();
        refreshList();

        datePicker.setValue(null);
        timePicker.getSelectionModel().clearSelection();

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Slot added successfully!");
    }

    @FXML
    private void removeSlot() {

        String selected = availabilityList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            messageLabel.setText("Please select a slot to remove.");
            return;
        }

        String[] parts = selected.split("  ");
        LocalDate date = LocalDate.parse(parts[0]);
        String time = parts[1];

        provider.removeAvailability(date, time);
        ServiceRegistry.userDb.save();

        refreshList();

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Slot removed.");
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("provider/ProviderDashboard.fxml");
    }
}
