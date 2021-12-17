package edu.rice.comp504.model.chatroom;


import edu.rice.comp504.model.NullUser;

/**
 * NullChatRoom represents an initial or unexpected chatroom type.
 */
public class NullChatRoom extends AChatRoom {
    private static NullChatRoom ONLY;

    /**
     * Constructor.
     */
    public NullChatRoom() {
        super(-1, NullUser.make(), 0, "none", "NullChatRoom", "null");
    }

    /**
     * Make a null object.  There is only one (singleton).
     * @return A null object
     */
    public static NullChatRoom make() {
        if (ONLY == null) {
            ONLY = new NullChatRoom();
        }
        return ONLY;
    }

    public void detectHateSpeech() {
    }
}
