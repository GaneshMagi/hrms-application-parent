package com.rbts.hrms.candidateonboarding.customexception;

public class DataIntegrityException extends Exception{

    private static final long serialVersionUID = -9079454849611061074L;

    public DataIntegrityException() {
        super();
    }

    public DataIntegrityException(final String message) {
        super(message);
    }
}
