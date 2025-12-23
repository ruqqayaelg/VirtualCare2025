package databases;

import model.EHR;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EHRDatabase extends BaseDatabase {

    private static final String FILE = "data/ehr.txt";
    private final List<EHR> ehrList = new ArrayList<>();

    public EHRDatabase() {
        ensureFileExists(FILE);
        load();
    }

    // ---------------------------
    // LOAD FROM FILE
    // ---------------------------
    public void load() {
        ehrList.clear();

        for (String line : readLines(FILE)) {
            if (line == null || line.isBlank()) continue;

            String[] p = line.split(";");

            // EXPECTED FORMAT (7 fields):
            // id ; patientId ; providerId ; date ; diagnosis ; treatment ; notes
            if (p.length != 7) {
                System.out.println("Skipping invalid EHR line: " + line);
                continue;
            }

            try {
                String id = p[0];
                String patientId = p[1];
                String providerId = p[2];
                LocalDate date = LocalDate.parse(p[3]);
                String diagnosis = p[4];
                String treatment = p[5];
                String notes = p[6];

                ehrList.add(new EHR(
                        id,
                        patientId,
                        providerId,
                        date,
                        diagnosis,
                        treatment,
                        notes
                ));

            } catch (Exception e) {
                System.out.println("Error parsing EHR line: " + line);
            }
        }
    }

    // ---------------------------
    // SAVE TO FILE  ✅ FIXED
    // ---------------------------
    public void save() {
        List<String> out = new ArrayList<>();

        for (EHR e : ehrList) {
            out.add(String.join(";",
                    e.getId(),
                    e.getPatientId(),
                    e.getProviderId(),
                    e.getDate().toString(),
                    e.getDiagnosis(),
                    e.getTreatment(),
                    e.getNotes()
            ));
        }

        writeLines(FILE, out);
    }

    // ---------------------------
    // DATABASE OPERATIONS
    // ---------------------------
    public void add(EHR ehr) {
        ehrList.add(ehr);
        save();
    }

    public void update(EHR ehr) {
        save(); // object already updated in memory
    }

    public EHR findByPatientId(String patientId) {
        for (EHR e : ehrList) {
            if (e.getPatientId().equals(patientId)) {
                return e;
            }
        }
        return null;
    }

    public List<EHR> getForPatient(String patientId) {
        List<EHR> out = new ArrayList<>();
        for (EHR e : ehrList) {
            if (e.getPatientId().equals(patientId)) {
                out.add(e);
            }
        }
        return out;
    }

    // ---------------------------
    // DELETE EHR (REQUIRED)
    // ---------------------------
    public void deleteByPatientId(String patientId) {
        ehrList.removeIf(e -> e.getPatientId().equals(patientId));
        save();
    }
}
