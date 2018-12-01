package com.hl.exceptions;

/**
 * Exception that is thrown on error with update to the HL database.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class DatabaseUpdateException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -8272890184134509352L;

    public DatabaseUpdateException() {
        super();
    }

    public DatabaseUpdateException(String message) {
        super(message);
    }

}
