package edu.rice.comp504.model;

public class User {
    private int age;
    private String school;
    private String interests;
    private String password;
    private String username;
    private int userId;
    private String avatar;

    /**
     * Constructor.
     *
     * @param userId   The user id
     * @param username The username
     * @param password The user password
     * @param age      The user's age
     * @param school   The user's school
     * @param interest The user's interest
     */
    public User(int userId, String username, String password, int age, String school, String interest, String avatar) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.age = age;
        this.school = school;
        this.interests = interest;
        this.avatar = avatar;
    }

    public User() {


    }

    /**
     * Get the user age.
     *
     * @return The user age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the user age.
     *
     * @param age the new user age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Get the user school.
     * @return The user school
     */
    public String getSchool() {
        return school;
    }

    /**
     * Set the user school.
     * @param school the new user school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Get the user interests.
     * @return The user interests
     */
    public String getInterest() {
        return interests;
    }

    /**
     * Set the user interests.
     * @param interests the new user interests
     */
    public void setInterest(String interests) {
        this.interests = interests;
    }

    /**
     * Get the user password.
     *
     * @return The user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user password.
     *
     * @param password the new user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the user id.
     *
     * @return The user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set the user id.
     *
     * @param userId the new user id
     */
    //public void setUserId(int userId) {
        //this.userId = userId;
    //}

}
