package edu.rice.comp504.model;


/**
 * NullUser represents an initial or unexpected user type.
 */
public class NullUser extends User {
    private static NullUser ONLY;

    /**
     * Constructor.
     */
    public NullUser() {
        super(-1, "nullUser", "000000", 0, "none", "none", "none");
    }

    /**
     * Make a null object.  There is only one (singleton).
     * @return A null object
     */
    public static NullUser make() {
        if (ONLY == null) {
            ONLY = new NullUser();
        }
        return ONLY;
    }
}