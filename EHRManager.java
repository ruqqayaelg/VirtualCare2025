package service;

import databases.EHRDatabase;
import model.EHR;

import java.time.LocalDate;
import java.util.List;

public class EHRManager {

    private final EHRDatabase ehrDb;

    public EHRManager(EHRDatabase ehrDb) {
        this.ehrDb = ehrDb;
    }

    // -----------------------------
    // GET SINGLE EHR
    // -----------------------------
    public EHR getEHR(String patientId) {
        return ehrDb.findByPatientId(patientId);
    }

    // -----------------------------
    // CREATE OR UPDATE EHR
    // -----------------------------
    public void saveEHR(String patientId,
                        String providerId,
                        String diagnosis,
                        String treatment,
                        String notes) {

        EHR existing = ehrDb.findByPatientId(patientId);

        if (existing != null) {
            // Update existing record
            existing.setDiagnosis(diagnosis);
            existing.setTreatment(treatment);
            existing.setNotes(notes);
            existing.setDate(LocalDate.now());

            ehrDb.update(existing);
        } else {
            // Create new record
            String id = "EHR" + System.currentTimeMillis();

            EHR record = new EHR(
                    id,
                    patientId,
                    providerId,
                    LocalDate.now(),
                    diagnosis,
                    treatment,
                    notes
            );

            ehrDb.add(record);
        }
    }

    // -----------------------------
    // DELETE EHR (REQUIRED)
    // -----------------------------
    public void deleteEHR(String patientId) {
        ehrDb.deleteByPatientId(patientId);
    }

    // -----------------------------
    // GET HISTORY FOR PATIENT
    // -----------------------------
    public List<EHR> getRecordsForPatient(String patientId) {
        return ehrDb.getForPatient(patientId);
    }
}
