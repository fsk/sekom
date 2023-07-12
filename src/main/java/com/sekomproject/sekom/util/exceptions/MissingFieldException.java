package com.sekomproject.sekom.util.exceptions;

public class MissingFieldException extends RuntimeException {

    private String EX_MSG = ErrorMessages.MISSING_FIELD;

    public String getExMsg() {
        return EX_MSG;
    }
}
