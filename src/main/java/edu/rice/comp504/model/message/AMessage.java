package edu.rice.comp504.model.message;

import java.sql.Timestamp;

public abstract class AMessage {
    private Timestamp timestamp;
    private int id;
    private int senderId;
    private int roomId;
    private int receiverId;
    protected String type;

    /**
     * Constructor.
     * @param timestamp The time message sent.
     * @param id The message's unique id.
     * @param senderId The user who sent the message.
     * @param receiverId The user/room that receives the message.
     */
    public AMessage(Timestamp timestamp, int id, int senderId, int roomId, int receiverId) {
        this.timestamp = timestamp;
        this.id = id;
        this.senderId = senderId;
        this.roomId = roomId;
        this.receiverId = receiverId;
    }

    public AMessage() {
    }

    /**
     * Get the message timestamp.
     * @return The timestamp of the message
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Get the message id.
     * @return The unique id of the message.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the message sender.
     * @return The sender of the message.
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * Get the message chatroom.
     * @return The chatroom of the message.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Get the message receiver.
     * @return The receiver of the message.
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * Get the message type.
     * @return The type of the message.
     */
    public String getType() {
        return type;
    }

    /**
     * Detect whether there is hate speech in the message.
     * @return If the message contains hate speech.
     */
    public abstract boolean detectHateSpeech();
}
