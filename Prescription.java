package model;

import java.time.LocalDate;

public class Prescription {

    private String prescriptionId;
    private String patientId;
    private String patientName;
    private String medication;
    private String dosage;
    private LocalDate issueDate;
    private LocalDate endDate;
    private int dispensingLimit;
    private String providerId;   // issuing provider (ID)

    // -------------------------------------------------
    // CANONICAL CONSTRUCTOR (used by DB + managers)
    // -------------------------------------------------
    public Prescription(String prescriptionId,
                        String patientId,
                        String patientName,
                        String medication,
                        String dosage,
                        LocalDate issueDate,
                        LocalDate endDate,
                        int dispensingLimit,
                        String providerId) {

        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.medication = medication;
        this.dosage = dosage;
        this.issueDate = issueDate;
        this.endDate = endDate;
        this.dispensingLimit = dispensingLimit;
        this.providerId = providerId;
    }

    // -------------------------------------------------
    // CONVENIENCE CONSTRUCTOR (new prescriptions)
    // -------------------------------------------------
    public Prescription(String patientId,
                        String patientName,
                        String medication,
                        String dosage,
                        LocalDate issueDate,
                        LocalDate endDate) {

        this(
                "RX" + System.currentTimeMillis(),
                patientId,
                patientName,
                medication,
                dosage,
                issueDate,
                endDate,
                1,      // default dispensing limit
                null    // providerId set later
        );
    }

    // --------------------------
    // GETTERS
    // --------------------------
    public String getPrescriptionId() { return prescriptionId; }
    public String getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getMedication() { return medication; }
    public String getDosage() { return dosage; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getDispensingLimit() { return dispensingLimit; }
    public String getProviderId() { return providerId; }

    // --------------------------
    // SETTERS
    // --------------------------
    public void setProviderId(String providerId) { this.providerId = providerId; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
