package ui.patient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.common.DashboardNavigator;

public class PatientConsultationController {

    @FXML private Button backBtn;

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("ui/patient/PatientDashboard.fxml");
    }
}
