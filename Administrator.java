package model;

public class Administrator extends User {

    public Administrator(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    // UML methods
    public void registerHealthcareProvider(HealthcareProvider provider) {}
    public void manageProviders() {}
    public void monitorSystemFeedback() {}

    @Override
    public String toString() {
        return "Admin[" + super.toString() + "]";
    }
}
