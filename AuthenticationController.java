package service;

import databases.UserDatabase;
import model.User;

public class AuthenticationController {

    private final UserDatabase userDb;

    private User loggedInUser;  // ⭐ store the current user

    public AuthenticationController(UserDatabase userDb) {
        this.userDb = userDb;
    }

    /**
     * Returns the logged-in user object.
     */


    public User getLoggedInUser() {
        return loggedInUser;
    }

    public User login(String email, String password) {
        for (User u : userDb.findAll()) {
            if (u.login(email, password)) {
                loggedInUser = u;
                return u;
            }
        }
        return null;
    }


    /**
     * Logs out the current user.
     */
    public void logout() {
        loggedInUser = null;
    }
}
