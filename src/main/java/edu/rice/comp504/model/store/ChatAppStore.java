package edu.rice.comp504.model.store;

import edu.rice.comp504.model.User;
import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.message.AMessage;
import edu.rice.comp504.utils.Types;
import org.eclipse.jetty.websocket.api.Session;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 * A store containing current Chat App, which contains all the users and chat
 * rooms.
 */
public class ChatAppStore {
    private static ChatAppStore ONLY;
    private static Gson gson = new Gson();
    private PropertyChangeSupport pcs;

    /**
     * Constructor.
     */
    public ChatAppStore() {
        UserStore.make();
        UserStore.make().signup("Yi", "1", 18, "rice", "none", "avatar1.png");
        UserStore.make().signup("Shuai", "1", 18, "rice", "none", "avatar2.png");
        UserStore.make().signup("c", "c", 18, "rice", "none", "avatar3.png");
        ChatRoomStore.make();
        ChatRoomStore.make().createChatRoom(UserStore.make().getUser(1), 10,
                "test", "Test", "public", "");
        addUser(0, 2);
        addUser(0, 3);


        ChatRoomStore.make().createChatRoom(UserStore.make().getUser(1), 10,
                "test2", "Test2", "public", "");
        addUser(1, 3);


        ChatRoomStore.make().createChatRoom(UserStore.make().getUser(2), 2,
                "test3", "Test3", "public", "");
        addUser(2, 3);


        ChatRoomStore.make().createChatRoom(UserStore.make().getUser(2), 10,
                "test4", "Test4", "private", "");

        MessageService.make();
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Get the singleton ChatApp Store.
     *
     * @return The ChatApp Store
     */
    public static ChatAppStore make() {
        if (ONLY == null) {
            ONLY = new ChatAppStore();
        }
        return ONLY;
    }

    /**
     * Add an user.
     *
     * @param chatRoomId chat room id
     * @param userId     user id
     * @return a string
     */
    public String addUser(int chatRoomId, int userId) {
        AChatRoom room = ChatRoomStore.make().getRoom(chatRoomId);

        if (room == null) {
            return "wrong room id";
        }
        return room.addUser(UserStore.make().getUser(userId));
    }

    /**
     * Remove a specific user from a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String removeUser(int chatRoomId, int userId) {
        AChatRoom room = ChatRoomStore.make().getRoom(chatRoomId);
        if (room == null) {
            return "wrong room id";
        }
        return room.removeUser(userId);
//        }
    }

    /**
     * Remove a specific user from a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public List<Session> closeRoom(int chatRoomId, int userId) {
        AChatRoom room = ChatRoomStore.make().getRoom(chatRoomId);
//        if (room == null) {
//            return "wrong room id";
//        }
        List<Session> tmp = UserStore.make().getChatroomSession(chatRoomId);
        ChatRoomStore.make().deleteRoom(chatRoomId);
        return tmp;
    }

    /**
     * Block a specific user in a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String blockUser(int chatRoomId, int userId) {
        AChatRoom room = ChatRoomStore.make().getRoom(chatRoomId);
        if (room == null) {
            return "wrong room id";
        }
        String resp = room.blockUser(userId);
        if (resp.equals(Types.SUCESS)) {
            sendMessage(Types.ACTION, Types.BAN, userId, chatRoomId, userId);
        }
        return resp;
    }

    /**
     * Block a specific user in a specific chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String unblockUser(int chatRoomId, int userId) {
        AChatRoom room = ChatRoomStore.make().getRoom(chatRoomId);
        if (room == null) {
            return "wrong room id";
        }
        return room.unblockUser(userId);
    }

    /**
     * Set a user as the administrator in the chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public String addAdmin(int chatRoomId, int userId) {
        AChatRoom room = ChatRoomStore.make().getRoom(chatRoomId);
        if (room == null) {
            return "wrong room id";
        }
        return room.addAdmin(userId);
    }

    /**
     * Check if a specific user has joined the chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public boolean checkUser(int chatRoomId, int userId) {
        return ChatRoomStore.make().getRoom(chatRoomId).checkUser(userId);
    }

    /**
     * Check if a specific user is the admin of the chat room.
     *
     * @param chatRoomId The id of the chat room.
     * @param userId     The id of the user.
     */
    public boolean checkAdmin(int chatRoomId, int userId) {
        return ChatRoomStore.make().getRoom(chatRoomId).isAdmin(userId);
    }


    /**
     * User send a message in a room/ to another user.
     *
     * @param type       The type of the message.
     * @param data       The data of the message.
     * @param sender     The sender of the message.
     * @param chatRoomId The chat room that the message is sent.
     * @param receiver   The receiver of the message.
     * @return msgId if the message is sent successfully, 0 if the message is
     *               NOT sent successfully.
     */
    public int sendMessage(String type, String data, int sender,
                           int chatRoomId, int receiver) {
        AMessage msg = MessageService.make().makeMessage(type, data,
                UserStore.make().getUser(sender),
                ChatRoomStore.make().getRoom(chatRoomId),
                UserStore.make().getUser(receiver));
        return MessageService.make().sendMessage(sender, chatRoomId,
                msg.getId());
    }

    /**
     * Send session message.
     * @param type type
     * @param data data
     * @param sender sender
     * @param chatRoomId chatroom id
     * @param receiver receiver
     * @param sessions sessions
     * @return message code
     */
    public int sendSessionMessage(String type, String data, int sender,
                                  int chatRoomId, int receiver,
                                  List<Session> sessions) {
        AMessage msg = MessageService.make().makeMessage(type, data,
                UserStore.make().getUser(sender),
                ChatRoomStore.make().getRoom(chatRoomId),
                UserStore.make().getUser(receiver));
        return MessageService.make().sendSessionMessage(sender, chatRoomId,
                msg.getId(), sessions);
    }

    /**
     * User recall a message that belongs to him.
     *
     * @param sender   The sender of the message.
     * @param chatRoom The chat room that the message is sent.
     * @param receiver The receiver of the message.
     */
    public void recallMessage(User sender, AChatRoom chatRoom, User receiver) {
        MessageService.make().recallMessage(sender.getUserId(),
                chatRoom.getId(), receiver.getUserId());
    }

    /**
     * Operate message.
     * @param chatRoomId chat room id
     * @param userId user id
     * @param msgId message id
     * @param type type
     */
    public void operateMessage(int chatRoomId, int userId, int msgId,
                               String type) {
        switch (type) {
            case "recall":
                MessageService.make().recallMessage(chatRoomId, userId, msgId);
                break;
            case "delete":
                MessageService.make().deleteMessage(chatRoomId, userId, msgId);
                break;
            default:
                break;
        }
    }


    /**
     * Admin delete a message in a chatroom.
     *
     * @param sender   The sender of the message.
     * @param chatRoom The chat room that the message is sent.
     * @param receiver The receiver of the message.
     */
    public void deleteMessage(User sender, AChatRoom chatRoom, User receiver) {
        MessageService.make().deleteMessage(sender.getUserId(),
                chatRoom.getId(), receiver.getUserId());
    }

    /**
     * Add a new user to the store.
     *
     * @param user   The user object.
     * @param userId The user id.
     */
    public void createUser(int userId, User user) {
        UserStore.make().createUser(userId, user);
    }


    /**
     * Signup a new user by a username and password.
     *
     * @param userName The input username
     * @param password The input password
     * @return If the username is available.
     */
    public int signup(String userName, String password, int age,
                      String school, String interests, String avatar) {
        return UserStore.make().signup(userName, password, age, school,
                interests, avatar);
    }

    /**
     * Login based on username and password.
     *
     * @param userName The user's username
     * @param password The user's password
     * @return If the username and password are correct.
     */
    public int login(String userName, String password) {
        return UserStore.make().login(userName, password);
    }

    /**
     * Get a user based on the id.
     *
     * @param userId The user id.
     * @return The user.
     */
    public User getUser(int userId) {
        return UserStore.make().getUser(userId);
    }

    /**
     * Get a room based on the id.
     *
     * @param roomId The room id.
     * @return The room.
     */
    public AChatRoom getRoom(int roomId) {
        return ChatRoomStore.make().getRoom(roomId);
    }

    /**
     * Get user list based on the room id.
     *
     * @param roomId The room id.
     * @return The user list.
     */
    public List<User> getUserList(int roomId) {
        return ChatRoomStore.make().getRoom(roomId).getAll();
    }

    /**
     * Get room list based.
     *
     * @return The room list.
     */
    public List<AChatRoom> getRoomList() {
        return new ArrayList<>(ChatRoomStore.make().chatRoomMap.values());
    }

    /**
     * Get room list based.
     */
    public int createRoom(int userId, int size, String description,
                          String name, String type,
                          String password) {
        if (UserStore.make().getUser(userId) == null) {
            return 0;
        }
        int roomId =
                ChatRoomStore.make().createChatRoom(UserStore.make().getUser(userId), size, description, name, type, password).getId();
        addUser(roomId, userId);
        return roomId;
    }

    /**
     * On sessions.
     *
     * @param userId  user id
     * @param session session
     */
    public void onSession(int userId, Session session) {
        UserStore.make().onSession(userId, session);
    }

    /**
     * Off sessions.
     *
     * @param userId user id
     */
    public void offSession(int userId) {
        UserStore.make().offSession(userId);
    }
}
