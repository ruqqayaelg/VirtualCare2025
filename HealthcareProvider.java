package model;

import model.enums.SpecialtyType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HealthcareProvider extends User {

    private SpecialtyType specialty;

    // Availability as objects
    private List<AvailabilitySlot> availabilitySlots = new ArrayList<>();

    public HealthcareProvider(String id, String name, String email, String password, SpecialtyType specialty) {
        super(id, name, email, password);
        this.specialty = specialty;
    }

    public SpecialtyType getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyType specialty) {
        this.specialty = specialty;
    }

    // ---------------------------------------------------
    //              AVAILABILITY SLOT CLASS
    // ---------------------------------------------------
    public static class AvailabilitySlot {

        private LocalDate date;
        private String time;

        public AvailabilitySlot(LocalDate date, String time) {
            this.date = date;
            this.time = time;
        }

        public LocalDate getDate() { return date; }
        public String getTime() { return time; }

        @Override
        public String toString() {
            return date + " " + time;
        }
    }

    // ---------------------------------------------------
    //              AVAILABILITY METHODS
    // ---------------------------------------------------

    public void addAvailability(LocalDate date, String time) {
        availabilitySlots.add(new AvailabilitySlot(date, time));
    }

    public void addAvailabilitySlot(AvailabilitySlot slot) {
        availabilitySlots.add(slot);
    }

    public void removeAvailability(LocalDate date, String time) {
        availabilitySlots.removeIf(slot ->
                slot.getDate().equals(date) && slot.getTime().equals(time)
        );
    }

    public boolean isAvailable(LocalDate date, String time) {
        return availabilitySlots.stream()
                .anyMatch(slot ->
                        slot.getDate().equals(date) && slot.getTime().equals(time)
                );
    }

    public List<AvailabilitySlot> getAvailabilitySlots() {
        return availabilitySlots;
    }

    // Convert slot list → string list (for DB saving)
    public List<String> getAvailabilityAsStringList() {
        List<String> list = new ArrayList<>();
        for (AvailabilitySlot slot : availabilitySlots) {
            list.add(slot.getDate().toString() + "," + slot.getTime());
        }
        return list;
    }

    // Load slot list from DB strings
    public void loadAvailabilityFromStrings(List<String> data) {
        for (String s : data) {
            if (s == null || s.isBlank()) continue;
            String[] parts = s.split(",");
            if (parts.length == 2) {
                LocalDate date = LocalDate.parse(parts[0]);
                String time = parts[1];
                availabilitySlots.add(new AvailabilitySlot(date, time));
            }
        }
    }
    @Override
    public String toString() {
        return name + " (" + specialty + ")";
    }

}
