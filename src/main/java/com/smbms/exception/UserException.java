package com.smbms.exception;

public class UserException extends Exception{
    private String message;

    public UserException() {
    }

    public UserException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
