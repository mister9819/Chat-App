package edu.rice.comp504.model.message;

import java.sql.Timestamp;

public class TextMessage extends AMessage {

    private String text;

    /**
     * A text message contains a plain text
     *
     * @param timestamp The time message sent.
     * @param id        The message's unique id.
     * @param senderId    The user who sent the message.
     * @param receiverId  The user/room that receives the message.
     */
    public TextMessage(Timestamp timestamp, int id, int senderId, int roomId, int receiverId, String text) {
        super(timestamp, id, senderId, roomId, receiverId);
        this.text = text;
        this.type = "text";
    }

    /**
     * @return The plain text.
     */
    public String getText() {
        return text;
    }

    /**
     * @param text The plain text.
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean detectHateSpeech() {
//        this.text = this.text.replaceAll("hate speech", "**** *****");
        return this.text.contains("hate speech");
    }

}
