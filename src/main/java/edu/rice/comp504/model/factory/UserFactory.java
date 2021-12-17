package edu.rice.comp504.model.factory;

import edu.rice.comp504.model.User;

public class UserFactory {
    private static UserFactory ONLY;

    /**
     * Get the singleton user factory.
     *
     * @return The singleton user factory.
     */
    public static UserFactory make() {
        if (ONLY == null) {
            ONLY = new UserFactory();
        }
        return ONLY;
    }

    /**
     * Create a new user.
     *
     * @return A user.
     */
    public User createUser(int id, String username, String password, int age, String school, String interest, String avatar) {
        return new User(id, username, password, age, school, interest, avatar);
    }
}
