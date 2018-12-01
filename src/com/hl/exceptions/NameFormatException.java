package com.hl.exceptions;

/**
 * Exception that is thrown on error with parsing a person's full name.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class NameFormatException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -7100541837917247249L;

    public NameFormatException() {
        super();
    }
    
    public NameFormatException(String msg) {
        super(msg);
    }

}
