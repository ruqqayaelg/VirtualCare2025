package service;

import databases.AppointmentDatabase;
import databases.UserDatabase;
import model.*;
import model.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentManager {

    private final AppointmentDatabase appointmentDb;
    private final UserDatabase userDb;

    public AppointmentManager(UserDatabase userDb, AppointmentDatabase appointmentDb) {
        this.userDb = userDb;
        this.appointmentDb = appointmentDb;
    }

    // ------------------------------------
    // BOOK APPOINTMENT
    // ------------------------------------
    public Appointment bookAppointment(Patient patient,
                                       HealthcareProvider provider,
                                       LocalDate date,
                                       String time,
                                       String appointmentId) {

        Appointment appt = new Appointment(
                appointmentId,
                patient,
                provider,
                date,
                time,
                AppointmentStatus.SCHEDULED
        );

        appointmentDb.add(appt);
        return appt;
    }

    // ------------------------------------
    // STATUS CHANGES
    // ------------------------------------
    public void cancelAppointment(Appointment appt) {
        appt.setStatus(AppointmentStatus.CANCELLED);
        appointmentDb.save();
    }

    public void completeAppointment(Appointment appt) {
        appt.setStatus(AppointmentStatus.COMPLETED);
        appointmentDb.save();
    }

    // ------------------------------------
    // AUTO-MARK MISSED
    // ------------------------------------
    public void autoDetectMissedAppointments() {

        LocalDate today = LocalDate.now();

        for (Appointment a : appointmentDb.findAll()) {
            if (a.getDate().isBefore(today)
                    && a.getStatus() == AppointmentStatus.SCHEDULED) {

                a.updateStatus(AppointmentStatus.MISSED);
            }
        }

        appointmentDb.save();
    }

    // ------------------------------------
    // READ-ONLY QUERIES (UI SUPPORT)
    // ------------------------------------

    // ✅ USED BY PatientAppointmentsController
    public List<Appointment> getAppointmentsForPatient(String patientId) {
        return appointmentDb.findAll()
                .stream()
                .filter(a -> a.getPatient().getId().equals(patientId))
                .collect(Collectors.toList());
    }

    // (Optional – future provider views)
    public List<Appointment> getAppointmentsForProvider(String providerId) {
        return appointmentDb.findAll()
                .stream()
                .filter(a -> a.getProvider().getId().equals(providerId))
                .collect(Collectors.toList());
    }

    // ------------------------------------
    public List<Appointment> getAllAppointments() {
        return appointmentDb.findAll();
    }

    public void save() {
        appointmentDb.save();
    }
}
