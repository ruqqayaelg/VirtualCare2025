package service;

import databases.*;
import model.User;

public class ServiceRegistry {

    // ============================
    // DATABASES
    // ============================
    public static final UserDatabase userDb;
    public static final AppointmentDatabase appointmentDb;
    public static final EHRDatabase ehrDb;
    public static final BillingDatabase billingDb;
    public static final FeedbackDatabase feedbackDb;
    public static final PrescriptionDatabase prescriptionDb;

    // ============================
    // SERVICES
    // ============================
    public static final AuthenticationController authenticationController;
    public static final AppointmentManager appointmentManager;
    public static final EHRManager ehrManager;
    public static final BillingService billingService;
    public static final ProviderManager providerManager;
    public static final AdminManager adminManager;
    public static final PrescriptionManager prescriptionManager;
    public static final FeedbackProcessor feedbackProcessor;

    static {

        // -------- DATABASES --------
        userDb = new UserDatabase();
        appointmentDb = new AppointmentDatabase(userDb);
        ehrDb = new EHRDatabase();
        billingDb = new BillingDatabase(userDb);
        feedbackDb = new FeedbackDatabase(userDb);
        prescriptionDb = new PrescriptionDatabase();

        // -------- SERVICES --------
        authenticationController = new AuthenticationController(userDb);
        appointmentManager = new AppointmentManager(userDb, appointmentDb);
        ehrManager = new EHRManager(ehrDb);
        billingService = new BillingService(billingDb);
        providerManager = new ProviderManager(userDb);
        adminManager = new AdminManager(userDb);
        prescriptionManager = new PrescriptionManager(prescriptionDb);
        feedbackProcessor = new FeedbackProcessor(feedbackDb);
    }

    private ServiceRegistry() {
        // Prevent instantiation
    }
}
