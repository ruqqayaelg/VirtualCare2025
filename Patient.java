package model;

public class Patient extends User {

    private String medicalHistory;
    private String contactInfo;

    // Link patient to provider (implementation detail)
    private String assignedProviderId;

    // -------------------------------------------------
    // MAIN CONSTRUCTOR (used by DB / provider assignment)
    // -------------------------------------------------
    public Patient(String id,
                   String name,
                   String email,
                   String password,
                   String medicalHistory,
                   String contactInfo,
                   String assignedProviderId) {

        super(id, name, email, password);
        this.medicalHistory = medicalHistory;
        this.contactInfo = contactInfo;
        this.assignedProviderId = assignedProviderId;
    }

    // -------------------------------------------------
    // CONVENIENCE CONSTRUCTOR (used during self-registration)
    // -------------------------------------------------
    public Patient(String id,
                   String name,
                   String email,
                   String password,
                   String medicalHistory,
                   String contactInfo) {

        this(
                id,
                name,
                email,
                password,
                medicalHistory,
                contactInfo,
                null   // provider not assigned yet
        );
    }

    // --------------------------
    // GETTERS
    // --------------------------
    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAssignedProviderId() {
        return assignedProviderId;
    }

    // --------------------------
    // SETTERS
    // --------------------------
    public void setMedicalHistory(String history) {
        this.medicalHistory = history;
    }

    public void setContactInfo(String info) {
        this.contactInfo = info;
    }

    public void setAssignedProviderId(String providerId) {
        this.assignedProviderId = providerId;
    }
}
