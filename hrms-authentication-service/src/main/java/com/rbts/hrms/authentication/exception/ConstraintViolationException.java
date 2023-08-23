package com.rbts.hrms.authentication.exception;

public class ConstraintViolationException extends Exception{


    private static final long serialVersionUID = -9079454849611061074L;

    public ConstraintViolationException() {
        super();
    }

    public ConstraintViolationException(final String message) {
        super(message);
    }
}
