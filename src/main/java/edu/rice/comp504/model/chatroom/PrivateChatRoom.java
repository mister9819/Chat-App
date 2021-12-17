package edu.rice.comp504.model.chatroom;

import edu.rice.comp504.model.User;

//todo: actually private rooms are by invitation only.
// no password needed
public class PrivateChatRoom extends AChatRoom {
    private String passHash;

    public PrivateChatRoom(int id, User admins, int size, String description, String name, String passHash) {
        super(id, admins, size, description, name, "private");
        this.passHash = passHash;
    }

    /**
     * Get the chatroom passHash.
     *
     * @return The chatroom passHash
     */
    public String getPassHash() {
        return passHash;
    }

    /**
     * Set the chatroom passHash.
     *
     * @param passHash the new chatroom passHash
     */
    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }
}
