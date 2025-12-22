package service;

import model.PharmacySystem;
import model.Prescription;

public class PharmacyIntegrationService {

    private final PharmacySystem pharmacySystem = new PharmacySystem();

    public void sendPrescriptionToPharmacy(Prescription prescription) {
        pharmacySystem.receivePrescription(prescription);
    }

    public String trackPrescription(String prescriptionId) {
        return pharmacySystem.accessPrescription(prescriptionId);
    }

    // ---------------------------------------------------------
    // NEW: DISPENSE PRESCRIPTION WITH LIMIT ENFORCEMENT
    // ---------------------------------------------------------
    public void dispensePrescription(Prescription prescription) {

        if (!pharmacySystem.canDispense(prescription)) {
            throw new IllegalStateException(
                    "Dispensing limit reached for this prescription."
            );
        }

        // Record successful dispense
        pharmacySystem.recordDispense(prescription);
    }
}
