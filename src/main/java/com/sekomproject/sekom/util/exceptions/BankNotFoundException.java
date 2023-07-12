package com.sekomproject.sekom.util.exceptions;

public class BankNotFoundException extends RuntimeException{

    private final String bankName;

    public BankNotFoundException(String bankName) {
        this.bankName = bankName;
    }


    public String getExMsg() {
        return this.bankName + " " + ErrorMessages.BANK_NOT_FOUND;

    }

}
