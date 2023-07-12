package com.sekomproject.sekom.util.exceptions;

public class BankAccountOwnerNotFoundException extends RuntimeException {

    private final String uniqueBankAccountOwnerKey;

    public BankAccountOwnerNotFoundException(String uniqueBankAccountOwnerKey) {
        this.uniqueBankAccountOwnerKey = uniqueBankAccountOwnerKey;
    }

    public String getExMsg() {
        return this.uniqueBankAccountOwnerKey + " " + ErrorMessages.BANK_ACCOUNT_OWNER_NOT_FOUND;
    }
}
