package com.sekomproject.sekom.util.exceptions;

import java.util.UUID;


public class BankAccountOwnerNotFoundException extends RuntimeException {

    private UUID uniqueBankAccountOwnerKey;
    private String identityNumber;

    public BankAccountOwnerNotFoundException(UUID uniqueBankAccountOwnerKey) {
        this.uniqueBankAccountOwnerKey = uniqueBankAccountOwnerKey;
    }

    public BankAccountOwnerNotFoundException(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getExMsgUniqueBankAccountOwnerKey() {
        return this.uniqueBankAccountOwnerKey + " " + ErrorMessages.BANK_ACCOUNT_OWNER_NOT_FOUND;
    }

    public String getExMsgIdentityNumber() {
        return this.identityNumber + " " + ErrorMessages.BANK_ACCOUNT_OWNER_NOT_FOUND;
    }
}
