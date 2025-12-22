package ui.patient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Bill;
import model.enums.BillStatus;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class BillingPaymentController {

    @FXML private Label billInfoLabel;
    @FXML private TextField cardNumberField;
    @FXML private TextField expiryField;
    @FXML private PasswordField cvvField;
    @FXML private TextField nameField;
    @FXML private Label messageLabel;

    private Bill billToPay;

    @FXML
    public void initialize() {

        billToPay = DashboardNavigator.getSelectedBill();

        if (billToPay == null) {
            billInfoLabel.setText("No bill selected.");
            return;
        }

        billInfoLabel.setText(
                "Paying " + billToPay.getAmount()
                        + " EGP to " + billToPay.getProvider().getName()
        );
    }

    @FXML
    private void processPayment() {

        if (billToPay == null) return;

        billToPay.updateStatus(BillStatus.PAID);
        ServiceRegistry.billingDb.save();

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Payment Successful!");
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("patient/PatientBills.fxml");
    }
}
