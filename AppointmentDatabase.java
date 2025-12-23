package databases;

import model.*;
import model.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDatabase extends BaseDatabase {

    private static final String FILE = "data/appointments.txt";
    private final List<Appointment> appointments = new ArrayList<>();

    public AppointmentDatabase(UserDatabase userDb) {
        ensureFileExists(FILE);
        load(userDb);
    }

    // -------------------------
    // LOAD
    // -------------------------
    public void load(UserDatabase userDb) {

        appointments.clear();

        for (String line : readLines(FILE)) {
            if (line.isBlank()) continue;

            // id;patientId;providerId;date;time;status;notes
            String[] p = line.split(";");
            if (p.length < 6) continue;

            String id = p[0];
            Patient patient = (Patient) userDb.findById(p[1]);
            HealthcareProvider provider =
                    (HealthcareProvider) userDb.findById(p[2]);

            LocalDate date = LocalDate.parse(p[3]);
            String time = p[4];
            AppointmentStatus status = AppointmentStatus.valueOf(p[5]);
            String notes = p.length > 6 ? p[6] : "";

            Appointment appt = new Appointment(
                    id, patient, provider, date, time, status
            );
            appt.setConsultationNotes(notes);

            appointments.add(appt);
        }
    }

    // -------------------------
    // SAVE
    // -------------------------
    public void save() {

        List<String> out = new ArrayList<>();

        for (Appointment a : appointments) {
            out.add(String.join(";",
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getProviderId(),
                    a.getDate().toString(),
                    a.getTime(),
                    a.getStatus().name(),
                    a.getConsultationNotes()
            ));
        }

        writeLines(FILE, out);
    }

    // -------------------------
    // CRUD
    // -------------------------
    public void add(Appointment a) {
        appointments.add(a);
        save();
    }

    public List<Appointment> findAll() {
        return appointments;
    }
}
