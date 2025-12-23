package databases;

import java.util.ArrayList;
import java.util.List;

public class PaymentTransactionDatabase extends BaseDatabase {

    private static final String FILE = "data/payments.txt";
    private final List<String> transactions = new ArrayList<>();

    public PaymentTransactionDatabase() {
        ensureFileExists(FILE);
        load();
    }

    public void load() {
        transactions.clear();
        transactions.addAll(readLines(FILE));
    }

    public void save() {
        writeLines(FILE, transactions);
    }

    public void add(String recordLine) {
        transactions.add(recordLine);
        save();
    }

    public List<String> findAll() {
        return transactions;
    }
}
