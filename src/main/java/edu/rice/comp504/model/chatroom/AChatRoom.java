package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.User;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.utils.Types;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AChatRoom {

    private int id;
    private ConcurrentHashMap<Integer, AMessage> messageMap;
    private ConcurrentHashMap<Integer, User> users;
    private HashSet<Integer> blockedUsers;
    private Set<Integer> admin;
    private int size;
    private String description;
    private ConcurrentHashMap<Integer, Timestamp> userMap;
    private String name;
    private String type;
//    private HashSet<Integer> warnedUsers;

    /**
     * userMap      A treemap that stores the user and their first enter timestamp.
     * messageMap   A hashmap that stores the unique id of message and messages in this chatroom.
     * users        A hashmap that stores the unique id of user and users in this chatroom.
     * blockedUsers A hashset that stores the blocked users.
     *
     * @param id          The unique id of the chatroom.
     * @param admins      The administrator of this chat room.
     * @param size        The size of the chat room.
     * @param description The description of the chat room.
     * @param name        The name of the chatroom.
     * @param type        The type of the chatroom.
     */
    public AChatRoom(int id, User admins, int size, String description, String name, String type) {
        this.id = id;
        this.messageMap = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
        this.users.put(admins.getUserId(), admins);
        this.blockedUsers = new HashSet<>();
        this.admin = new HashSet<>();
        this.admin.add(admins.getUserId());
        this.size = size;
        this.description = description;
        this.userMap = new ConcurrentHashMap<>();
        this.name = name;
        this.type = type;
//        this.warnedUsers = new HashSet<>();
        userMap.put(admins.getUserId(), new Timestamp(System.currentTimeMillis()));
    }

    public AChatRoom() {

    }

    /**
     * @param id The user's id.
     * @return The user corresponding to that id.
     */
    public User getUser(int id) {
        return users.get(id);
    }

    /**
     * Add a new user to the room
     *
     * @param user the new user.
     */
    public String addUser(User user) {
        if (checkUser(user.getUserId())) {
            return "already in the room";
        }
        if (users.size() == size) {
            return "Room is full";
        }
        users.put(user.getUserId(), user);
        userMap.put(user.getUserId(), new Timestamp(System.currentTimeMillis()));
        return Types.SUCESS;
    }

    /**
     * Block a user.
     */
    public String blockUser(int userId) {
        if (!users.containsKey(userId)) {
            return "not in the room";
        }
        if (blockedUsers.contains(userId)) {
            return "already blocked";
        }
        blockedUsers.add(userId);
        return Types.SUCESS;
    }

    /**
     * Unblock a user.
     */
    public String unblockUser(int userId) {
        if (!users.containsKey(userId)) {
            return "not in the room";
        }
        if (!blockedUsers.contains(userId)) {
            return "not blocked";
        }
        blockedUsers.remove(userId);
        return Types.SUCESS;
    }

    /**
     * Remove a user.
     */
    public String removeUser(int userId) {
        if (!users.containsKey(userId)) {
            return "wrong user id";
        }
        users.remove(userId);
        admin.remove(userId);
        userMap.remove(userId);
        return Types.SUCESS;
    }

    /**
     * Add a chatroom admin.
     *
     * @param userId the new chatroom admin
     */
    public String addAdmin(int userId) {
        if (!users.containsKey(userId)) {
            return "not in the room";
        }
        if (admin.contains(userId)) {
            return "already exists";
        }
        this.admin.add(userId);
        return Types.SUCESS;
    }

    /**
     * Get the chatroom admins.
     *
     * @return The chatroom admins
     */
    public Set<Integer> getAdmin() {
        return admin;
    }

    /**
     * Get the chatroom size.
     *
     * @return The chatroom size
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the chatroom size.
     *
     * @param size the new chatroom size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Get the chatroom description.
     *
     * @return The chatroom description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the chatroom description.
     *
     * @param description the new chatroom description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the chatroom name.
     *
     * @return The chatroom name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the chatroom name.
     *
     * @param name the new chatroom name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get all the messages in the room.
     *
     * @return the message map.
     */
    public ConcurrentHashMap<Integer, AMessage> getMessageMap() {
        return messageMap;
    }

    /**
     * @param message The incoming message.
     */
    public void receiveMessage(AMessage message) {
        this.messageMap.put(message.getId(), message);
    }

    public void deleteMessage(int id) {
        this.messageMap.remove(id);
    }

    /**
     * Get the chatroom id.
     *
     * @return The chatroom id
     */
    public int getId() {
        return id;
    }

    /**
     * Get all the users in the room.
     *
     * @return A list of users.
     */
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Check if the user has joined the room.
     *
     * @param userId The user to be checked.
     * @return If the user joined the room
     */
    public boolean checkUser(int userId) {
        return users.containsKey(userId);
    }

    /**
     * Check if user is blocked.
     *
     * @param userId The user to be checked.
     * @return If the user has been blocked in the room.
     */
    public boolean checkBlock(int userId) {
        return blockedUsers.contains(userId);
    }

    /**
     * Get the chatroom type.
     *
     * @return The chatroom type
     */
    public String getType() {
        return type;
    }


    /**
     * Check if a user is the admin of this chatroom.
     *
     * @return the result
     */
    public boolean isAdmin(int userId) {
        return this.admin.contains(userId);
    }

    /**
     * Get user's message.
     *
     * @return the message list
     */
    public List<AMessage> getUserMessage(int userId) {
        Timestamp tmp = this.userMap.get(userId);
        ArrayList<AMessage> msgList = new ArrayList<>();
        Object[] keys = this.messageMap.keySet().toArray();
        Arrays.sort(keys);
        for (Object i : keys) {
            if (tmp == null || this.messageMap.get(i).getTimestamp().compareTo(tmp) > 0) {
                msgList.add(this.messageMap.get(i));
            }
        }
        return msgList;
    }

}
