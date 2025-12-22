package ui.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Bill;
import model.User;
import model.Appointment;

public class DashboardNavigator {

    private static Stage stage;
    private static User currentUser;

    // ✅ ADD THIS
    private static Appointment selectedAppointment;

    // =========================
    // STAGE HANDLING
    // =========================
    public static void setMainStage(Stage primaryStage) {
        stage = primaryStage;
    }

    // =========================
    // USER SESSION
    // =========================
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    // =========================
    // APPOINTMENT CONTEXT ✅
    // =========================
    public static void setSelectedAppointment(Appointment appointment) {
        selectedAppointment = appointment;
    }

    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    // =========================
    // NAVIGATION
    // =========================
    public static void logout() {
        currentUser = null;
        selectedAppointment = null;
        navigateTo("common/Login.fxml");
    }

    public static void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    DashboardNavigator.class.getResource("/ui/" + fxmlPath)
            );
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("❌ ERROR loading FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
    private static Bill selectedBill;

    public static void setSelectedBill(Bill bill) {
        selectedBill = bill;
    }

    public static Bill getSelectedBill() {
        return selectedBill;
    }

}
