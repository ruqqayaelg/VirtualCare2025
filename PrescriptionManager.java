package service;

import databases.PrescriptionDatabase;
import model.Prescription;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionManager {

    private final PrescriptionDatabase prescriptionDb;

    public PrescriptionManager(PrescriptionDatabase prescriptionDb) {
        this.prescriptionDb = prescriptionDb;
    }

    // --------------------------------------------------------------------
    // ISSUE A NEW PRESCRIPTION (explicit dispensing limit + providerId)
    // --------------------------------------------------------------------
    public void issuePrescription(String patientId,
                                  String patientName,
                                  String medication,
                                  String dosage,
                                  LocalDate issueDate,
                                  LocalDate endDate,
                                  int dispensingLimit,
                                  String providerId) {

        // Auto-generate a unique prescription ID
        String id = "RX" + System.currentTimeMillis();

        Prescription p = new Prescription(
                id,
                patientId,
                patientName,
                medication,
                dosage,
                issueDate,
                endDate,
                dispensingLimit,
                providerId
        );

        prescriptionDb.add(p);
    }

    // --------------------------------------------------------------------
    // GET ALL PRESCRIPTIONS FOR A PATIENT
    // --------------------------------------------------------------------
    public List<Prescription> getPrescriptionsForPatient(String patientId) {
        return prescriptionDb.getByPatientId(patientId);
    }

    // --------------------------------------------------------------------
    // FIND A PRESCRIPTION BY ID
    // --------------------------------------------------------------------
    public Prescription findById(String id) {
        return prescriptionDb.findById(id);
    }

    // --------------------------------------------------------------------
    // GET ALL PRESCRIPTIONS
    // --------------------------------------------------------------------
    public List<Prescription> findAll() {
        return prescriptionDb.findAll();
    }

    // --------------------------------------------------------------------
    // RENEW A PRESCRIPTION (date only)
    // --------------------------------------------------------------------
    public void renewPrescription(Prescription p) {
        LocalDate newEnd = p.getEndDate().plusMonths(1);
        p.setEndDate(newEnd);
        prescriptionDb.save();
    }

    // --------------------------------------------------------------------
    // DELETE A PRESCRIPTION
    // --------------------------------------------------------------------
    public void delete(String id) {
        prescriptionDb.delete(id);
    }
}
