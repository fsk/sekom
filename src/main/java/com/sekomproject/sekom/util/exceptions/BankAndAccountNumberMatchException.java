package com.sekomproject.sekom.util.exceptions;

public class BankAndAccountNumberMatchException extends RuntimeException{

    private String bankName;
    private String accountNumber;

    public BankAndAccountNumberMatchException(String bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    public String getExtMsg() {
        return this.bankName + " " + this.accountNumber + " " + ErrorMessages.BANK_AND_BANK_ACCOUNT_DONT_MATCH;
    }
}
