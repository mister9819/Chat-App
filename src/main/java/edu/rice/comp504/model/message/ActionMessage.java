package edu.rice.comp504.model.message;

import java.sql.Timestamp;

public class ActionMessage extends AMessage {
    private String action;

    /**
     * Constructor.
     *
     * @param timestamp  The time message sent.
     * @param id         The message's unique id.
     * @param senderId   The user who sent the message.
     * @param receiverId The user/room that receives the message.
     */
    public ActionMessage(Timestamp timestamp, int id, int senderId, int roomId, int receiverId, String data) {
        super(timestamp, id, senderId, roomId, receiverId);
        this.action = data;
        this.type = "action";
    }

    @Override
    public boolean detectHateSpeech() {
        return false;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }
}
