package edu.rice.comp504.model.command;

import edu.rice.comp504.model.message.AMessage;

/**
 * The IMessageCmd is an interface used to pass commands to users in the MessageService.  The
 * message must execute the command.
 */
public interface IMessageCmd {
    /**
     * Execute the command.
     * @param context The receiver on which the command is executed.
     */
    public void execute(AMessage context);
}
