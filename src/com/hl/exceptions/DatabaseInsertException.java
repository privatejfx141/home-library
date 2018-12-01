package com.hl.exceptions;

/**
 * Exception that is thrown on error with insertion to the HL database.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class DatabaseInsertException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -7207009425098455759L;

    public DatabaseInsertException() {
        super();
    }
    
    public DatabaseInsertException(String message) {
        super(message);
    }
    
}
