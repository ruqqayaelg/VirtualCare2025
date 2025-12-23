package databases;

import model.*;
import model.enums.BillStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class BillingDatabase extends BaseDatabase {

    private static final String FILE_PATH = "data/bills.txt";
    private final List<Bill> bills = new ArrayList<>();

    private final UserDatabase userDb;

    public BillingDatabase(UserDatabase userDb) {
        ensureFileExists(FILE_PATH);
        this.userDb = userDb;
        load();
    }

    public void load() {
        bills.clear();

        for (String line : readLines(FILE_PATH)) {
            String[] p = line.split(";");
            if (p.length < 6) continue;

            String id = p[0];
            Patient patient = (Patient) userDb.findById(p[1]);
            HealthcareProvider provider = (HealthcareProvider) userDb.findById(p[2]);
            double amount = Double.parseDouble(p[3]);
            BillStatus status = BillStatus.valueOf(p[4]);
            LocalDate date = LocalDate.parse(p[5]);

            // Correct constructor order:
            // Bill(id, patient, provider, amount, date, status)
            bills.add(new Bill(id, patient, provider, amount, date, status));
        }
    }

    public void save() {

        List<String> lines = new ArrayList<>();

        for (Bill bill : bills) {

            // 🔒 Skip corrupted bills safely
            if (bill == null ||
                    bill.getPatient() == null ||
                    bill.getProvider() == null) {
                continue;
            }

            lines.add(
                    bill.getBillId() + "," +
                            bill.getPatient().getId() + "," +
                            bill.getProvider().getId() + "," +
                            bill.getAmount() + "," +
                            bill.getStatus()
            );
        }

        try {
            Files.write(
                    Paths.get(FILE_PATH),
                    lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Bill bill) {

        // 🔒 Never store invalid bills
        if (bill == null || bill.getPatient() == null || bill.getProvider() == null) {
            return;
        }

        bills.add(bill);
        save();
    }


    public List<Bill> findAll() {
        return bills;
    }

    public Bill findById(String id) {
        return bills.stream()
                .filter(b -> b.getBillId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public String generateBillId() {
        return "B" + (bills.size() + 1);
    }

}
