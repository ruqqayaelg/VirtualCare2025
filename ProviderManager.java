package service;

import databases.UserDatabase;
import model.HealthcareProvider;
import model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class ProviderManager {

    private final UserDatabase userDb;

    public ProviderManager(UserDatabase userDb) {
        this.userDb = userDb;
    }

    public List<HealthcareProvider> getAllProviders() {
        return userDb.findAll().stream()
                .filter(u -> u instanceof HealthcareProvider)
                .map(u -> (HealthcareProvider) u)
                .collect(Collectors.toList());
    }

    public List<Patient> getPatientsOfProvider(HealthcareProvider provider) {
        return userDb.findAll().stream()
                .filter(u -> u instanceof Patient)
                .map(u -> (Patient) u)
                .collect(Collectors.toList());
    }
}
