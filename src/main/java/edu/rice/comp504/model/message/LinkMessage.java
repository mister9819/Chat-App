package edu.rice.comp504.model.message;

import java.sql.Timestamp;


public class LinkMessage extends AMessage {
    private String text;

    /**
     * A link message contains a link
     *
     * @param timestamp The time message sent.
     * @param id        The message's unique id.
     * @param senderId    The user who sent the message.
     * @param receiverId  The user/room that receives the message.
     */
    public LinkMessage(Timestamp timestamp, int id, int senderId, int roomId, int receiverId, String text) {
        super(timestamp, id, senderId, roomId, receiverId);
        this.text = text;
        this.type = "link";
    }

    /**
     * @return The link.
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The link.
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean detectHateSpeech() {
        return false;
    }

}