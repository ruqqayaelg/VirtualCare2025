package model;

import java.time.LocalDate;

public class EHR {

    private String id;
    private String patientId;     // Only store IDs (cleaner for saving to file)
    private String providerId;    // NEW ✔ needed for UI & history display
    private LocalDate date;
    private String diagnosis;
    private String treatment;
    private String notes;

    public EHR(String id,
               String patientId,
               String providerId,
               LocalDate date,
               String diagnosis,
               String treatment,
               String notes) {

        this.id = id;
        this.patientId = patientId;
        this.providerId = providerId;
        this.date = date;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
    }

    // ----- GETTERS -----

    public String getId() { return id; }

    public String getPatientId() { return patientId; }

    public String getProviderId() { return providerId; } // ✔ Used in PatientEHRController

    public LocalDate getDate() { return date; }

    public String getDiagnosis() { return diagnosis; }

    public String getTreatment() { return treatment; }

    public String getNotes() { return notes; }

    // ----- SETTERS -----

    public void setDate(LocalDate date) { this.date = date; }

    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public void setTreatment(String treatment) { this.treatment = treatment; }

    public void setNotes(String notes) { this.notes = notes; }
}
