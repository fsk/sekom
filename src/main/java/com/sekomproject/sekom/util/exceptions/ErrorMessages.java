package com.sekomproject.sekom.util.exceptions;

public class ErrorMessages {

    private ErrorMessages(){}

    public static final String DUPLICATE_CONSTRAINT = "Already Exist.!";

    public static final String BANK_NOT_FOUND = "Bank Not Found.!";

    public static final String MISSING_FIELD = "Missing Field.!";

    public static final String BANK_ACCOUNT_OWNER_NOT_FOUND = """
            The unique account owner key that you provided as a parameter could not locate an account owner,
            or the account owner has not yet created a record in any existing bank.
            """;

    public static final String BANK_ACCOUNT_NOT_FOUND = "Bank Account Not Found.!";
}
