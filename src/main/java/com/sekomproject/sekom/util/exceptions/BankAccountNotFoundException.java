package com.sekomproject.sekom.util.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BankAccountNotFoundException extends RuntimeException{

    private String accountNumber;

    public String getExtMsg() {
        return this.accountNumber + " " + ErrorMessages.BANK_ACCOUNT_NOT_FOUND;
    }

}
