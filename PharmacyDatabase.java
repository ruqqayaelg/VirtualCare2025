package databases;

import java.util.ArrayList;
import java.util.List;
import java.time.YearMonth;
import java.time.LocalDate;


public class PharmacyDatabase extends BaseDatabase {

    private static final String FILE = "data/pharmacy.txt";
    private final List<String> records = new ArrayList<>();

    public PharmacyDatabase() {
        ensureFileExists(FILE);
        load();
    }

    public void load() {
        records.clear();
        records.addAll(readLines(FILE));
    }

    private void save() {
        List<String> lines = new ArrayList<>();

        for (String r : records) {
            lines.add(r);
        }

        writeLines(FILE, lines);
    }
    // ---------------------------------------------------------
// COUNT MONTHLY DISPENSE EVENTS FOR A PRESCRIPTION
// ---------------------------------------------------------
    // ---------------------------------------------------------
// COUNT MONTHLY DISPENSE EVENTS (MATCHES pharmacy.txt)
// ---------------------------------------------------------
    public int countMonthlyDispenses(String prescriptionId, YearMonth month) {

        int count = 0;

        for (String line : records) {

            if (line == null || line.isBlank()) continue;

            String[] parts = line.split(";");

            // Minimum fields check (supports both 5 and 7 fields)
            if (parts.length < 5) continue;

            // prescriptionId is ALWAYS index 0
            if (!parts[0].equals(prescriptionId)) continue;

            // status is ALWAYS the LAST field
            String status = parts[parts.length - 1];
            if (!"DISPENSED".equals(status)) continue;

            // date is:
            // index 3 in old format (5 fields)
            // index 5 in new format (7 fields)
            String dateString = (parts.length == 5)
                    ? parts[3]
                    : parts[5];

            LocalDate dispenseDate = LocalDate.parse(dateString);

            if (YearMonth.from(dispenseDate).equals(month)) {
                count++;
            }
        }

        return count;
    }




    /**
     * line format:
     * prescriptionId;patientId;providerName;date;status
     */
    public void addRecord(String prescriptionId,
                          String patientId,
                          String providerId,
                          String medication,
                          String dosage,
                          String issueDate,
                          String status) {

        records.add(String.join(";",
                prescriptionId,
                patientId,
                providerId,
                medication,
                dosage,
                issueDate,
                status
        ));

        save();
    }


    public List<String> findAll() {
        return records;
    }

    /**
     * Returns a full record string for a prescription:
     * PR001;P001;Dr. Smith;2025-12-05;RECEIVED
     */
    public String findByPrescriptionId(String prescriptionId) {
        return records.stream()
                .filter(rec -> rec.startsWith(prescriptionId + ";"))
                .findFirst()
                .orElse(null);
    }
}
