package service;

import databases.UserDatabase;
import model.Administrator;
import model.HealthcareProvider;

import java.util.List;
import java.util.stream.Collectors;

public class AdminManager {

    private final UserDatabase userDb;

    public AdminManager(UserDatabase userDb) {
        this.userDb = userDb;
    }

    public void registerProvider(HealthcareProvider provider) {
        userDb.add(provider);
    }

    public List<HealthcareProvider> getAllProviders() {
        return userDb.findAll().stream()
                .filter(u -> u instanceof HealthcareProvider)
                .map(u -> (HealthcareProvider) u)
                .collect(Collectors.toList());
    }

    public void monitorFeedback() {
        // handled in FeedbackProcessor
    }
}
