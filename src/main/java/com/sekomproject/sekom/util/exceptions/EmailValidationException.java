package com.sekomproject.sekom.util.exceptions;

public class EmailValidationException extends RuntimeException{

    private String email;

    public EmailValidationException(String email) {
        this.email = email;
    }

    public String getExtMsg() {
        return this.email + " " + ErrorMessages.EMAIL_IS_NOT_VALID;
    }


}
