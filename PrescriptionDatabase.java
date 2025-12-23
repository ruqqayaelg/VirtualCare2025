package databases;

import model.Prescription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDatabase extends BaseDatabase {

    private static final String FILE = "data/prescriptions.txt";

    private final List<Prescription> prescriptions = new ArrayList<>();

    public PrescriptionDatabase() {
        ensureFileExists(FILE);
        load();
    }

    public void add(Prescription p) {
        prescriptions.add(p);
        save();
    }

    public List<Prescription> getByPatientId(String patientId) {
        List<Prescription> out = new ArrayList<>();
        for (Prescription p : prescriptions) {
            if (p.getPatientId().equals(patientId)) {
                out.add(p);
            }
        }
        return out;
    }

    public Prescription findById(String id) {
        for (Prescription p : prescriptions) {
            if (p.getPrescriptionId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Prescription> findAll() {
        return prescriptions;
    }

    // ---------------------------------------------------------
    // LOAD FROM FILE
    // ---------------------------------------------------------
    public void load() {
        prescriptions.clear();

        for (String line : readLines(FILE)) {
            if (line == null || line.isBlank()) continue;

            String[] parts = line.split(";");

            try {
                String id = parts[0];
                String patientId = parts[1];
                String patientName = parts[2];
                String medication = parts[3];
                String dosage = parts[4];
                LocalDate issueDate = LocalDate.parse(parts[5]);
                LocalDate endDate = LocalDate.parse(parts[6]);

                // dispensing limit (8th field)
                int dispensingLimit = (parts.length >= 8)
                        ? Integer.parseInt(parts[7])
                        : 1;

                // providerId (9th field)
                String providerId = (parts.length >= 9)
                        ? parts[8]
                        : null;

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

                prescriptions.add(p);

            } catch (Exception e) {
                System.out.println("Error parsing prescription line: " + line);
            }
        }
    }

    // ---------------------------------------------------------
    // SAVE TO FILE
    // ---------------------------------------------------------
    public void save() {
        List<String> out = new ArrayList<>();

        for (Prescription p : prescriptions) {
            out.add(String.join(";",
                    p.getPrescriptionId(),
                    p.getPatientId(),
                    p.getPatientName(),
                    p.getMedication(),
                    p.getDosage(),
                    p.getIssueDate().toString(),
                    p.getEndDate().toString(),
                    String.valueOf(p.getDispensingLimit()),
                    p.getProviderId()   // providerId LAST
            ));
        }

        writeLines(FILE, out);
    }

    public void delete(String id) {
        prescriptions.removeIf(p -> p.getPrescriptionId().equals(id));
        save();
    }
}
