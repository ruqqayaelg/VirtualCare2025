package model;

import model.enums.AppointmentStatus;
import java.time.LocalDate;

public class Appointment {

    private String appointmentId;
    private Patient patient;
    private HealthcareProvider provider;
    private LocalDate date;
    private String time;
    private AppointmentStatus status;

    // ✅ REQUIRED for consultation + database
    private String consultationNotes;

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public Appointment(String appointmentId,
                       Patient patient,
                       HealthcareProvider provider,
                       LocalDate date,
                       String time,
                       AppointmentStatus status) {

        this.appointmentId = appointmentId;
        this.patient = patient;
        this.provider = provider;
        this.date = date;
        this.time = time;
        this.status = status;
        this.consultationNotes = ""; // default
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public String getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthcareProvider getProvider() {
        return provider;
    }

    public String getPatientId() {
        return patient != null ? patient.getId() : null;
    }



    public String getProviderId() {
        return provider.getId();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    // -------------------------
    // SETTERS
    // -------------------------
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void updateStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setConsultationNotes(String notes) {
        this.consultationNotes = notes;
    }
}
