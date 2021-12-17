package edu.rice.comp504.model.message;

import java.sql.Timestamp;



public class InvitationMessage extends AMessage {
//    private int roomId;

    /**
     * Constructor.
     *
     * @param timestamp  The time message sent.
     * @param id         The message's unique id.
     * @param senderId   The user who sent the message.
     * @param receiverId The user/room that receives the message.
     */
    public InvitationMessage(Timestamp timestamp, int id, int senderId, int roomId, int receiverId, int data) {
        super(timestamp, id, senderId, roomId, receiverId);
//        this.roomId = data;
        this.type = "invitation";
    }

    @Override
    public boolean detectHateSpeech() {
        return false;
    }
}