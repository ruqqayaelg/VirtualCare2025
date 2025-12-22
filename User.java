package model;

public class User {

    protected String id;
    protected String name;
    protected String email;
    protected String password;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Login check used by AuthenticationController
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Getters + setters
    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
}
