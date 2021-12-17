package edu.rice.comp504.model.store;

import edu.rice.comp504.model.User;
import edu.rice.comp504.model.factory.UserFactory;
import org.eclipse.jetty.websocket.api.Session;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore {
    public static int userId = 0;
    private static UserStore ONLY;
    private static ConcurrentHashMap<Integer, User> userMap = new ConcurrentHashMap<>();
    private static HashSet<String> usernames = new HashSet<>();
    //    ConcurrentHashMap<Session, User> sessionDB = new
    //    ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Session> userDB = new ConcurrentHashMap<>();

    /**
     * @return Singleton store.
     */
    public static UserStore make() {
        if (ONLY == null) {
            ONLY = new UserStore();
        }
        return ONLY;

    }


    /**
     * Sign up an user.
     *
     * @param userName  username
     * @param password  password
     * @param age       age
     * @param school    school
     * @param interests interests
     * @param avatar    avatar
     * @return the user
     */
    public int signup(String userName, String password, int age,
                      String school, String interests, String avatar) {
        if (usernames.contains(userName)) {
            return -1;
        }
        userId++;
        User user = UserFactory.make().createUser(userId, userName, password,
                age, school, interests, avatar);
        userMap.put(userId, user);
        usernames.add(userName);
        return userId;
    }

    /**
     * Login based on username and password.
     *
     * @param userName The user's username
     * @param password The user's password
     * @return If the username and password are correct.
     */
    public int login(String userName, String password) {
        for (Map.Entry<Integer, User> e : userMap.entrySet()) {
            User user = e.getValue();
            if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                userMap.put(user.getUserId(), user);
                return user.getUserId();
            }
        }
        return -1;
    }

    /**
     * Log out an user.
     * @param userId user id
     */
    public void logout(int userId) {
        userMap.remove(userId);
    }

    /**
     * Get a user based on the id.
     *
     * @param userId The user id.
     * @return The user.
     */
    public User getUser(int userId) {
        return userMap.get(userId);
    }

    /**
     * Get a user session based on the id.
     *
     * @param userId The user id.
     * @return The user.
     */
    public Session getUserSession(int userId) {
        return userDB.get(userId);
    }

    /**
     * Get a user based on the id.
     *
     * @return The users.
     */
    public List<User> getAllUser() {
        return new ArrayList<>(userMap.values());
    }

    /**
     * Get chatroom sessions.
     * @param chatroomId chatroom id
     * @return all sessions
     */
    public ArrayList<Session> getChatroomSession(int chatroomId) {
        List<User> users = ChatRoomStore.make().getRoom(chatroomId).getAll();
        ArrayList<Session> ss = new ArrayList<>();
        for (User u : users) {
            if (userDB.containsKey(u.getUserId())) {
                ss.add(userDB.get(u.getUserId()));
            }
        }
        return ss;
    }

    /**
     * Create a user.
     * @param userId user id
     * @param user the user
     */
    public void createUser(int userId, User user) {
        userMap.put(userId, user);
    }

    /**
     * On session.
     * @param userId user id
     * @param session session
     */
    public void onSession(int userId, Session session) {
        userDB.put(userId, session);
    }

    /**
     * Off session.
     * @param userId user id
     */
    public void offSession(int userId) {
        userDB.remove(userId);
    }
}
