package databases;

import model.*;
import model.enums.RatingLevel;

import java.util.ArrayList;
import java.util.List;

public class FeedbackDatabase extends BaseDatabase {

    private static final String FILE = "data/feedback.txt";
    private final List<Feedback> feedback = new ArrayList<>();

    private final UserDatabase userDb;

    public FeedbackDatabase(UserDatabase userDb) {
        ensureFileExists(FILE);
        this.userDb = userDb;
        load();
    }

    public void load() {
        feedback.clear();

        for (String line : readLines(FILE)) {
            String[] p = line.split(";");
            if (p.length < 5) continue;

            Patient patient = (Patient) userDb.findById(p[1]);
            HealthcareProvider provider = (HealthcareProvider) userDb.findById(p[2]);

            feedback.add(new Feedback(
                    p[0], patient, provider,
                    RatingLevel.valueOf(p[3]),
                    p[4]
            ));
        }
    }

    public void save() {
        List<String> out = new ArrayList<>();

        for (Feedback f : feedback) {
            out.add(String.join(";",
                    f.getFeedbackId(),
                    f.getPatient().getId(),
                    f.getProvider().getId(),
                    f.getRating().name(),
                    f.toString()
            ));
        }

        writeLines(FILE, out);
    }

    public void add(Feedback f) {
        feedback.add(f);
        save();
    }

    public List<Feedback> findAll() {
        return feedback;
    }
}
