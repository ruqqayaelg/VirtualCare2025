package databases;

import model.*;
import model.enums.SpecialtyType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDatabase extends BaseDatabase {

    private static final String FILE = "data/users.txt";
    private static final String AVAIL_FILE = "data/provider_availability.txt";

    private final List<User> users = new ArrayList<>();

    public UserDatabase() {
        ensureFileExists(FILE);
        ensureFileExists(AVAIL_FILE);
        load();
    }

    // ============================
    // LOAD USERS
    // ============================
    public void load() {
        users.clear();

        for (String line : readLines(FILE)) {

            // PATIENT:
            // id;PATIENT;name;email;password;history;contact;providerId(optional)
            //
            // PROVIDER:
            // id;PROVIDER;name;email;password;specialty
            //
            // ADMIN:
            // id;ADMIN;name;email;password

            String[] p = line.split(";");
            if (p.length < 5) continue;

            String id = p[0];
            String role = p[1];
            String name = p[2];
            String email = p[3];
            String password = p[4];

            switch (role) {

                case "PATIENT" -> {
                    String history = p.length > 5 ? p[5] : "";
                    String contact = p.length > 6 ? p[6] : "";
                    String providerId = p.length > 7 ? p[7] : null;

                    users.add(new Patient(
                            id,
                            name,
                            email,
                            password,
                            history,
                            contact,
                            providerId
                    ));
                }

                case "PROVIDER" -> {
                    SpecialtyType specialty = SpecialtyType.valueOf(p[5]);
                    users.add(new HealthcareProvider(
                            id,
                            name,
                            email,
                            password,
                            specialty
                    ));
                }

                case "ADMIN" -> users.add(
                        new Administrator(id, name, email, password)
                );
            }
        }

        loadProviderAvailability();
    }

    // ============================
    // LOAD PROVIDER AVAILABILITY
    // ============================
    private void loadProviderAvailability() {

        for (String line : readLines(AVAIL_FILE)) {
            // Format: providerId;date;time
            String[] p = line.split(";");
            if (p.length < 3) continue;

            String providerId = p[0];
            LocalDate date = LocalDate.parse(p[1]);
            String time = p[2];

            User u = findById(providerId);

            if (u instanceof HealthcareProvider hp) {
                hp.addAvailability(date, time);
            }
        }
    }

    // ============================
    // SAVE PROVIDER AVAILABILITY
    // ============================
    public void saveProviderAvailability() {

        List<String> out = new ArrayList<>();

        for (User u : users) {

            if (u instanceof HealthcareProvider hp) {
                for (HealthcareProvider.AvailabilitySlot slot : hp.getAvailabilitySlots()) {
                    out.add(String.join(";",
                            hp.getId(),
                            slot.getDate().toString(),
                            slot.getTime()
                    ));
                }
            }
        }

        writeLines(AVAIL_FILE, out);
    }

    // ============================
    // SAVE ALL USERS
    // ============================
    public void save() {

        List<String> out = new ArrayList<>();

        for (User u : users) {

            if (u instanceof Patient p) {
                out.add(String.join(";",
                        p.getId(),
                        "PATIENT",
                        p.getName(),
                        p.getEmail(),
                        p.getPassword(),
                        p.getMedicalHistory(),
                        p.getContactInfo(),
                        p.getAssignedProviderId() == null ? "" : p.getAssignedProviderId()
                ));
            }

            if (u instanceof HealthcareProvider hp) {
                out.add(String.join(";",
                        hp.getId(),
                        "PROVIDER",
                        hp.getName(),
                        hp.getEmail(),
                        hp.getPassword(),
                        hp.getSpecialty().name()
                ));
            }

            if (u instanceof Administrator a) {
                out.add(String.join(";",
                        a.getId(),
                        "ADMIN",
                        a.getName(),
                        a.getEmail(),
                        a.getPassword()
                ));
            }
        }

        writeLines(FILE, out);
        saveProviderAvailability();
    }

    // ============================
    // FIND USER BY ID
    // ============================
    public User findById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ============================
    // RETURN ALL USERS
    // ============================
    public List<User> findAll() {
        return users;
    }

    // ============================
    // ADD USER
    // ============================
    public void add(User u) {
        users.add(u);
        save();
    }

    // ============================
    // GET ALL PATIENTS
    // ============================
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Patient p) {
                list.add(p);
            }
        }
        return list;
    }

    // ============================
    // GET PATIENTS FOR PROVIDER (NEW)
    // ============================
    public List<Patient> findPatientsByProvider(String providerId) {
        List<Patient> result = new ArrayList<>();

        for (User u : users) {
            if (u instanceof Patient p) {
                if (providerId.equals(p.getAssignedProviderId())) {
                    result.add(p);
                }
            }
        }
        return result;
    }

    public List<User> getAllUsers() {
        return users; // assuming your internal list is called "users"
    }

    public List<HealthcareProvider> getAllHealthcareProviders() {
        return users.stream()
                .filter(u -> u instanceof HealthcareProvider)
                .map(u -> (HealthcareProvider) u)
                .collect(Collectors.toList());
    }

    public void removeUser(String userId) {
        users.removeIf(u -> u.getId().equals(userId));
        save(); // if you already persist users
    }
    // ============================
// GENERATE NEXT PROVIDER ID (H001)
// ============================
    public String generateNextProviderId() {
        int max = 0;

        for (User u : users) {
            if (u instanceof HealthcareProvider) {
                String id = u.getId(); // H001
                if (id.startsWith("H")) {
                    try {
                        int num = Integer.parseInt(id.substring(1));
                        max = Math.max(max, num);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        return String.format("H%03d", max + 1);
    }

    // ============================
// GENERATE NEXT PATIENT ID (P001)
// ============================
    public String generateNextPatientId() {
        int max = 0;

        for (User u : users) {
            if (u instanceof Patient) {
                String id = u.getId();
                if (id.startsWith("P")) {
                    try {
                        int num = Integer.parseInt(id.substring(1));
                        max = Math.max(max, num);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        return String.format("P%03d", max + 1);
    }

    // ============================
// GENERATE NEXT ADMIN ID (U001)
// ============================
    public String generateNextAdminId() {
        int max = 0;

        for (User u : users) {
            if (u instanceof Administrator) {
                String id = u.getId();
                if (id.startsWith("U")) {
                    try {
                        int num = Integer.parseInt(id.substring(1));
                        max = Math.max(max, num);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        return String.format("U%03d", max + 1);
    }

}

