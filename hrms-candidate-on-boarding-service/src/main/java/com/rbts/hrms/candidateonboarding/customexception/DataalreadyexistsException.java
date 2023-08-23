package com.rbts.hrms.candidateonboarding.customexception;

public class DataalreadyexistsException extends Exception{
    private static final long serialVersionUID = -9079454849611061074L;

    public DataalreadyexistsException() {
        super();
    }

    public DataalreadyexistsException(final String message) {
        super(message);
    }
}
