package edu.rice.comp504.model.message;

import java.sql.Timestamp;

public class DirectMessage extends AMessage {
    private String text;
    /**
     * Constructor.
     * @param timestamp The time message sent.
     * @param id The message's unique id.
     * @param senderId The user who sent the message.
     * @param receiverId The user/room that receives the message.
     */
    public DirectMessage(Timestamp timestamp, int id, int senderId, int roomId, int receiverId, String text) {
        super(timestamp, id, senderId, roomId, receiverId);
        this.text = text;
        this.type = "direct";
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

//    @Override
//    public void detectHateSpeech() {
//        for (String hateWord : HateSpeechUtil.hateWords) {
//            String star = "*";
//            String placement = star.repeat(hateWord.length());
//            this.text = this.text.replaceAll(hateWord, placement);
//        }
//    }

    @Override
    public boolean detectHateSpeech() {
//        this.text = this.text.replaceAll("hate speech", "**** *****");
        return this.text.contains("hate speech");
    }
}
