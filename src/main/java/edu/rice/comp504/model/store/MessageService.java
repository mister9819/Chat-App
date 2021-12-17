package edu.rice.comp504.model.store;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.rice.comp504.model.User;
import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.factory.MessageFactory;
import edu.rice.comp504.model.message.*;
import edu.rice.comp504.utils.Types;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.p;

public class MessageService {
    private static MessageService ONLY;
    private static int msgCount = 1;
    private ConcurrentHashMap<Integer, AMessage> msgMap =
            new ConcurrentHashMap<>();

    /**
     * @return Singleton store.
     */
    public static MessageService make() {
        if (ONLY == null) {
            ONLY = new MessageService();
        }
        return ONLY;
    }

    /**
     * User send a message in a room/ to another user.
     *
     * @param type     The type of the message.
     * @param data     The data of the message.
     * @param sender   The sender of the message.
     * @param chatRoom The chat room that the message is sent.
     * @param receiver The receiver of the message.
     */
    public AMessage makeMessage(String type, String data, User sender,
                                AChatRoom chatRoom, User receiver) {
        AMessage newMsg = MessageFactory.make().create(msgCount, type, data,
                sender, chatRoom, receiver);
        msgMap.put(msgCount++, newMsg);
        return newMsg;
    }

    /**
     * Get a message based on the id.
     *
     * @param msgId The message id.
     * @return The message.
     */
    public AMessage getMsg(int msgId) {
        if (msgId > 0) {
            return msgMap.get(msgId);
        } else {
            return NullMessage.make();
        }
    }


    /**
     * Send a message.
     *
     * @param userId The user id.
     * @param roomId The room id.
     * @param msgId  The message id.
     * @return msgId if the message is sent successfully, 0 if the message is
     *             NOT sent successfully.
     */
    public int sendMessage(int userId, int roomId, int msgId) {
        User sender = UserStore.make().getUser(userId);

        AChatRoom chatRoom = ChatRoomStore.make().getRoom(roomId);
        ArrayList<Session> sessions =
                UserStore.make().getChatroomSession(roomId);
        AMessage msg = msgMap.get(msgId);
        if (msg.detectHateSpeech()) {
            if (ChatRoomStore.make().getWarnedUsers().contains(userId)) {
                List<AChatRoom> userRooms = ChatRoomStore.make().getUserRoom(userId);
                for (AChatRoom userRoom : userRooms) {
                    userRoom.blockUser(userId);
                }
                return -2;
            } else {
                ChatRoomStore.make().addWarnedUsers(userId);
                return -1;
            }

        }
//        msg.detectHateSpeech();

        if (msg.getType().equals(Types.DIRECT)) {
            ArrayList<Session> directSessions = new ArrayList<>();
            directSessions.add(UserStore.make().getUserSession(userId));
            directSessions.add(UserStore.make().getUserSession(msg.getReceiverId()));
//            String name = sender.getUsername();
            User receiver = UserStore.make().getUser(msg.getReceiverId());
            broadcastMessage(sender, receiver, msg, directSessions);
            return msg.getId();
        } else if (msg.getType().equals(Types.INVITATION)) {
            ArrayList<Session> invitationSessions = new ArrayList<>();
            invitationSessions.add(UserStore.make().getUserSession(userId));
            invitationSessions.add(UserStore.make().getUserSession(msg.getReceiverId()));
//            String name = sender.getUsername();
            User receiver = UserStore.make().getUser(msg.getReceiverId());
            //System.out.println("msgService: invitor is " + name);
            //System.out.println("msgService: receiver is " + receiver);
            broadcastMessage(sender, receiver, msg, invitationSessions);
            return msg.getId();
        } else if (msg.getType().equals(Types.ACTION) || (chatRoom.checkUser(userId) && (!chatRoom.checkBlock(userId)) && !ChatRoomStore.make().checkBlock(userId))) {
//            String name = sender.getUsername();
            User receiver = UserStore.make().getUser(msg.getReceiverId());
            chatRoom.receiveMessage(msg);
            broadcastMessage(sender, receiver, msg, sessions);
            return msg.getId();
//            switch (msg.getType()) {
//                case TYPES.TEXT: {
//                    chatRoom.receiveMessage(msg);
//                    broadcastMessage( name, ((TextMessage) msg).getText(),
//                    sessions);
//                    return msg.getId();
//                }
//                case TYPES.IMAGE: {
//                    chatRoom.receiveMessage(msg);
//                    broadcastMessage( name, ((ImageMessage) msg).getUrl(),
//                    sessions);
//                    return msg.getId();
//                }
//                case TYPES.DIRECT: {
//                    //TODO: send a direct message
//                    return msg.getId();
//                }
//                case TYPES.ACTION:{
//                    chatRoom.receiveMessage(msg);
//                    broadcastMessage(name, ((ActionMessage) msg).getAction
//                    (), sessions);
//                    return msg.getId();
//                }
//                default:
//                    return 0;
//            }
        } else {
            return 0;
        }
    }

    /**
     * Send session message.
     * @param userId user id
     * @param roomId room id
     * @param msgId message id
     * @param sessions sessions
     * @return message code
     */
    public int sendSessionMessage(int userId, int roomId, int msgId,
                                  List<Session> sessions) {
        AMessage msg = msgMap.get(msgId);
        User sender = UserStore.make().getUser(userId);
        User receiver = UserStore.make().getUser(msg.getReceiverId());
        broadcastMessage(sender, receiver, msg, (ArrayList<Session>) sessions);
        return msg.getId();
    }

    /**
     * Recall a message.
     *
     * @param userId The user id.
     * @param roomId The room id.
     * @param msgId  The message id.
     */
    public void recallMessage(int roomId, int userId, int msgId) {
        AChatRoom chatRoom = ChatRoomStore.make().getRoom(roomId);
        AMessage msg = msgMap.get(msgId);

        if (msg.getSenderId() == userId) {
            chatRoom.deleteMessage(msg.getId());
            //let other users know this user has recalled a message
            AMessage recall = this.makeMessage(Types.ACTION, Types.RECALL,
                    UserStore.make().getUser(userId),
                    ChatRoomStore.make().getRoom(roomId),
                    UserStore.make().getUser(userId));
            sendMessage(userId, roomId, recall.getId());
        }
    }

    /**
     * Delete a message.
     *
     * @param userId The user id.
     * @param roomId The room id.
     * @param msgId  The message id.
     */
    public void deleteMessage(int roomId, int userId, int msgId) {
        AChatRoom chatRoom = ChatRoomStore.make().getRoom(roomId);
        AMessage msg = msgMap.get(msgId);

        if (chatRoom.isAdmin(userId)) {
            chatRoom.deleteMessage(msg.getId());
        }
    }

    /**
     * Edit a message.
     *
     * @param userId The user id.
     * @param roomId The room id.
     * @param msgId  The message id.
     * @param newMsg The new message.
     */
    public void editMessage(int roomId, int userId, int msgId, String newMsg) {
        AMessage msg = msgMap.get(msgId);

        if (msg.getSenderId() == userId) {
            if (msg instanceof TextMessage) {
                TextMessage textMsg = (TextMessage) msg;
                textMsg.setText(newMsg);
            }
            sendMessage(userId, roomId, msgId);
        }
    }

    /**
     * Broadcast a message to all users.
     *
     * @param sender  The message sender.
     * @param message The message.
     */
    private void broadcastMessage(User sender, User receiver,
                                  AMessage message,
                                  ArrayList<Session> sessions) {
        Gson gson = new Gson();
        sessions.forEach(session -> {
            try {
                JsonObject jo = new JsonObject();
                jo.addProperty("sender", gson.toJson(sender));
                jo.addProperty("receiver", gson.toJson(receiver));
                jo.addProperty("message", gson.toJson(message));
                session.getRemote().sendString(String.valueOf(jo));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * Get direct message.
     * @param userId user id
     * @return a list of message
     */
    public List<AMessage> getDirectMessage(int userId) {
        ArrayList<AMessage> msgList = new ArrayList<>();
        for (AMessage message : this.msgMap.values()) {
            if (message.getType().equals(Types.DIRECT)) {
                if (message.getSenderId() == userId || message.getReceiverId() == userId) {
                    msgList.add(message);
                }
            }
        }
        return msgList;
    }


}
