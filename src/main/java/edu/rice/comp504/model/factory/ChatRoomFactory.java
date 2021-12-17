package edu.rice.comp504.model.factory;

import edu.rice.comp504.model.User;
import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.chatroom.PrivateChatRoom;
import edu.rice.comp504.model.chatroom.PublicChatRoom;

public class ChatRoomFactory {
    private static ChatRoomFactory ONLY;

    /**
     * Get the singleton chat room factory.
     *
     * @return The singleton chat room factory
     */
    public static ChatRoomFactory make() {
        if (ONLY == null) {
            ONLY = new ChatRoomFactory();
        }
        return ONLY;
    }

    /**
     * make a chat room.
     *
     * @return a new chat room.
     */
    public AChatRoom make(int id, User admins, int size, String description, String name, String type,
                          String password) {
        AChatRoom newChatRoom;
        if (type.equals("public")) {
            newChatRoom = new PublicChatRoom(id, admins, size, description, name);
        } else if (type.equals("private")) {
            newChatRoom = new PrivateChatRoom(id, admins, size, description, name, password);
        } else {
            throw new java.lang.Error("Room type error");
        }
        return newChatRoom;
    }

}
