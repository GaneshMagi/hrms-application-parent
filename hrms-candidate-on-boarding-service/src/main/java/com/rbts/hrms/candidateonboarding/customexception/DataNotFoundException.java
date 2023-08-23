package com.rbts.hrms.candidateonboarding.customexception;

public class DataNotFoundException extends Exception {

    private static final long serialVersionUID = -9079454849611061074L;

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(final String message) {
        super(message);
    }

}

