package edu.rice.comp504.model.message;

import edu.rice.comp504.model.NullUser;

import java.sql.Timestamp;


/**
 * NullMessage represents an initial or unexpected message type.
 */
public class NullMessage extends AMessage {
    private static NullMessage ONLY;

    /**
     * Constructor.
     */
    public NullMessage() {
        super(new Timestamp(System.currentTimeMillis()), 0, -1, -1, -1);
        this.type = "null";
    }

    /**
     * Make a null object.  There is only one (singleton).
     * @return A null object
     */
    public static NullMessage make() {
        if (ONLY == null) {
            ONLY = new NullMessage();
        }
        return ONLY;
    }

    @Override
    public boolean detectHateSpeech() {
        return false;
    }
}