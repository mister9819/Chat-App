package edu.rice.comp504.model.command;

import edu.rice.comp504.model.chatroom.AChatRoom;

/**
 * The IChatRoomCmd is an interface used to pass commands to chat rooms in the ChatRoomStore.  The
 * chat room must execute the command.
 */
public interface IChatRoomCmd {
    /**
     * Execute the command.
     * @param context The receiver on which the command is executed.
     */
    public void execute(AChatRoom context);
}
