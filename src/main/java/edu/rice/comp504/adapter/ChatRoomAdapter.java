package edu.rice.comp504.adapter;

import edu.rice.comp504.model.User;
import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.model.message.TextMessage;
import edu.rice.comp504.model.store.ChatAppStore;
import edu.rice.comp504.model.store.ChatRoomStore;
import edu.rice.comp504.model.store.MessageService;
import edu.rice.comp504.model.store.UserStore;
import edu.rice.comp504.utils.Types;
import org.eclipse.jetty.websocket.api.Session;

import java.util.List;

/**
 * This adapter interfaces with view, controller and models on the top level.
 */
public class ChatRoomAdapter {
    private final ChatAppStore chatAppStore;

    public ChatRoomAdapter() {
        chatAppStore = ChatAppStore.make();
    }

    /**
     * Add a specific user to a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String addUser(int chatRoomId, int userId) {
        String resp = chatAppStore.addUser(chatRoomId, userId);
        if (resp.equals("success")) {
            sendMessage(Types.ACTION, Types.JOIN,userId,chatRoomId,userId);
            sendMessage(Types.ACTION, Types.RELOADROOMLIST,userId,chatRoomId,userId);
            sendMessage(Types.ACTION, Types.RELOADUSER,userId,chatRoomId,userId);

        }
        return resp;
    }

    /**
     * A specific user create a new chat room.
     *
     * @param userId The id of the user.
     */
    public int createRoom(int userId, int size, String description, String name, String type,
                          String password) {
        return chatAppStore.createRoom(userId, size, description, name, type,
                password);
    }

    /**
     * Remove a specific user from a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String removeUser(int chatRoomId, int userId) {
        return chatAppStore.removeUser(chatRoomId, userId);
    }

    /**
     * Block a specific user in a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String blockUser(int chatRoomId, int userId) {
        return chatAppStore.blockUser(chatRoomId, userId);
    }

    /**
     * Unblock a specific user in a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String unblockUser(int chatRoomId, int userId) {
        return chatAppStore.unblockUser(chatRoomId, userId);
    }

    /**
     * add a user as the administrator in the chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String addAdmin(int chatRoomId, int userId) {
        String resp = chatAppStore.addAdmin(chatRoomId, userId);
        if (resp.equals("success")) {
            sendMessage(Types.ACTION, Types.RELOADUSER,userId,chatRoomId,userId);
        }
        return resp;
    }

    /**
     * Check if a specific user has joined the chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public boolean checkUser(int chatRoomId, int userId) {
        return chatAppStore.checkUser(chatRoomId, userId);
    }


    /**
     * User send a message in a room/ to another user.
     *
     * @param type     The type of the message.
     * @param data     The data of the message.
     * @param sender   The sender of the message.
     * @param chatRoomId The chat room that the message is sent.
     * @param receiver The receiver of the message.
     * @return msgId if the message is sent successfully, 0 if the message is NOT sent successfully.
     */

    public int sendMessage(String type, String data, int sender, int chatRoomId, int receiver) {
        return chatAppStore.sendMessage(type, data, sender, chatRoomId, receiver);
    }

    public int sendSessionMessage(String type, String data, int sender,
                                  int chatRoomId, int receiver, List<Session> sessions) {
        return chatAppStore.sendSessionMessage(type, data, sender, chatRoomId, receiver, sessions);
    }

    /**
     * User recall a message that belongs to him.
     *
     * @param userId     The sender of the message.
     * @param chatRoomId The chat room that the message is sent.
     * @param msgId      The message Id.
     */
    public void operateMessage(int chatRoomId, int userId, int msgId, String type) {
        chatAppStore.operateMessage(chatRoomId, userId, msgId, type);
        sendMessage(Types.ACTION, Types.RELOADROOM,userId,chatRoomId,userId);

    }

    /**
     * Edit messages.
     *
     * @param chatRoomId chat room id
     * @param userId user id
     * @param msgId message id
     * @param val value
     */
    public void editMessage(int chatRoomId, int userId, int msgId,
                            String val) {
        TextMessage tmp = (TextMessage) ChatRoomStore.make().getRoom(chatRoomId).getMessageMap().get(msgId);
        tmp.setText(val);

        sendMessage(Types.ACTION, Types.RELOADROOM,userId,chatRoomId,userId);

    }

    /**
     * Add a new user to the store.
     *
     * @param user   The user object.
     * @param userId The user id.
     */
    public void createUser(int userId, User user) {
        chatAppStore.createUser(userId, user);
    }

    /**
     * Signup a new user by a username and password.
     *
     * @param userName The input username
     * @param password The input password
     * @return If the username is available.
     */
    public int signup(String userName, String password, int age, String school, String interest, String avatar) {
        return chatAppStore.signup(userName, password, age, school, interest, avatar);
    }

    /**
     * Login based on username and password.
     *
     * @param userName The user's username
     * @param password The user's password
     * @return If the username and password are correct.
     */
    public int login(String userName, String password) {
        return chatAppStore.login(userName, password);
    }

    /**
     * Get a user based on the id.
     *
     * @param userId The user id.
     * @return The user.
     */
    public User getUser(int userId) {
        return chatAppStore.getUser(userId);
    }

    /**
     * Get a chatroom based on the id.
     *
     * @param roomId The room id.
     * @return The chatroom.
     */
    public AChatRoom getChatroom(int roomId) {
        return chatAppStore.getRoom(roomId);
    }

    /**
     * Get a user list within a room.
     *
     * @param roomId The room id.
     * @return The list of users.
     */
    public List<User> getUserList(int roomId) {
        return chatAppStore.getUserList(roomId);
    }

    /**
     * Get room list based.
     *
     * @return The room list.
     */
    public List<AChatRoom> getRoomList() {
        return chatAppStore.getRoomList();
    }

    /**
     * Leave a room list.
     */
    public String leaveRoom(int chatRoomId, int userId) {
        String resp =  chatAppStore.removeUser(chatRoomId, userId);
        if (resp.equals("success")) {
            sendMessage(Types.ACTION, Types.LEAVE,userId,chatRoomId,userId);
            sendMessage(Types.ACTION, Types.RELOADUSER,userId,chatRoomId,userId);
            sendMessage(Types.ACTION, Types.RELOADROOMLIST,userId,chatRoomId,userId);
        }
        return resp;
    }
    /**
     * Leave all rooms.
     */
    public String leaveAllRoom(int userId) {
        List<AChatRoom> allRooms = getUserChatRoom(userId);
        for (AChatRoom room: allRooms) {
            leaveRoom(room.getId(), userId);
        }
        return "user" + UserStore.make().getUser(userId).getUsername() + "left all chatrooms";
    }

    /**
     * Close a room list.
     */
    public String closeRoom(int chatRoomId, int userId) {
        List<Session> resp =  chatAppStore.closeRoom(chatRoomId, userId);
        System.out.println("close room");
        System.out.println(resp);
        sendSessionMessage(Types.ACTION, Types.RELOADROOMLIST,userId,chatRoomId,userId,resp);
        sendSessionMessage(Types.ACTION, Types.RELOADROOM,userId,chatRoomId,userId,resp);
        sendSessionMessage(Types.ACTION, Types.RELOADUSER,userId,chatRoomId,userId,resp);
        return Types.SUCESS;
    }

    /**
     * Get user list.
     *
     * @return The user list.
     */
    public List<User> getAllUser() {
        return UserStore.make().getAllUser();
    }

    /**
     * Get user list.
     *
     * @return The user list.
     */
    public List<AChatRoom> getUserChatRoom(int userId) {
        return ChatRoomStore.make().getUserRoom(userId);
    }

    /**
     * Get user list.
     *
     * @return The user list.
     */
    public List<AChatRoom> getJoinChatRoom(int userId) {
        return ChatRoomStore.make().getJoinRoom(userId);
    }
    /**
     * Get user list.
     *
     * @return The user list.
     */
    public List<AMessage> getUserMessage(int userId, int chatRoomId) {
        return ChatRoomStore.make().getRoom(chatRoomId).getUserMessage(userId);
    }

    /**
     * Get direct message.
     * @param userId user id
     * @return list of message
     */
    public List<AMessage> getDirectMessage(int userId) {
        return MessageService.make().getDirectMessage(userId);
    }

    public int inviteJoinChatroom(int invitorId, int receiverId, int roomId) {
        return chatAppStore.sendMessage("invitation", "1", invitorId, roomId, receiverId);
    }
}