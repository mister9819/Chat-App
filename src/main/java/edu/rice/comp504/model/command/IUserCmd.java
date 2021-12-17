package edu.rice.comp504.model.command;

import edu.rice.comp504.model.User;

/**
 * The IUserCmd is an interface used to pass commands to users in the UserStore.  The
 * User must execute the command.
 */
public interface IUserCmd {
    /**
     * Execute the command.
     * @param context The receiver on which the command is executed.
     */
    public void execute(User context);
}
