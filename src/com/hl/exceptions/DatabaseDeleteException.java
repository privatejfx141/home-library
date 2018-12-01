package com.hl.exceptions;

/**
 * Exception that is thrown on error with deletion from the HL database.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class DatabaseDeleteException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 6102431986131871449L;

    public DatabaseDeleteException() {
        super();
    }

    public DatabaseDeleteException(String message) {
        super(message);
    }

}
