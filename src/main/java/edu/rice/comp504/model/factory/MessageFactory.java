package edu.rice.comp504.model.factory;

import edu.rice.comp504.model.User;
import edu.rice.comp504.model.chatroom.AChatRoom;
import edu.rice.comp504.model.message.*;
import edu.rice.comp504.utils.Types;

import java.sql.Timestamp;

public class MessageFactory {
    private static MessageFactory ONLY;

    /**
     * Get the singleton message factory.
     *
     * @return The singleton message factory.
     */
    public static MessageFactory make() {
        if (ONLY == null) {
            ONLY = new MessageFactory();
        }
        return ONLY;
    }

    /**
     * Create a message based on type.
     *
     * @param type     The type of the message.
     * @param data     The data of the message.
     * @param sender   The sender of the message.
     * @param chatRoom The chat room that the message is sent.
     * @param receiver The receiver of the message.
     * @return A message.
     */
    public AMessage create(int msgId, String type, String data, User sender,
                           AChatRoom chatRoom, User receiver) {
        int senderId = sender.getUserId();
        int roomId;
        if (chatRoom == null) {
            roomId = -1;
        } else {
            roomId = chatRoom.getId();
        }
        int receiverId = receiver.getUserId();
        switch (type) {
            case Types.ACTION: {
                return new ActionMessage(new Timestamp(System.currentTimeMillis()), msgId, senderId, roomId, receiverId, data);
            }
            case Types.DIRECT: {
                return new DirectMessage(new Timestamp(System.currentTimeMillis()), msgId, senderId, roomId, receiverId, data);
            }
            case Types.IMAGE: {
                return new ImageMessage(new Timestamp(System.currentTimeMillis()), msgId, senderId, roomId, receiverId, data);
            }
            case Types.TEXT: {
                return new TextMessage(new Timestamp(System.currentTimeMillis()), msgId, senderId, roomId, receiverId, data);
            }
            case Types.LINK: {
                return new LinkMessage(new Timestamp(System.currentTimeMillis()), msgId, senderId, roomId, receiverId, data);
            }
            case Types.INVITATION: {
                Integer integer;
                integer = Integer.valueOf(data);
                return new InvitationMessage(new Timestamp(System.currentTimeMillis()), msgId, senderId, roomId, receiverId, integer.intValue());
            }
            default: {
                return NullMessage.make();
            }
        }

    }
}
