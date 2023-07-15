package com.sekomproject.sekom.util.exceptions;


import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

public class CannotWithdrawException extends RuntimeException {

    private BigDecimal requestBalance;
    private BigDecimal dbBalance;

    public CannotWithdrawException(BigDecimal balance, BigDecimal totalBalanceByOwnerUUID) {
        this.requestBalance = balance;
        this.dbBalance = totalBalanceByOwnerUUID;
    }


    public String getExMsg() {
        return ErrorMessages.INSUFFICIENT_BALANCE + " Request Balance: " + requestBalance.toString() + " " +
                "Your Total Balance: " + dbBalance.toString();
    }

}
