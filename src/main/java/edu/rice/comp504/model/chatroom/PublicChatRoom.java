package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.User;

public class PublicChatRoom extends AChatRoom {

    public PublicChatRoom(int id, User admins, int size, String description, String name) {
        super(id, admins, size, description, name, "public");
    }
}
