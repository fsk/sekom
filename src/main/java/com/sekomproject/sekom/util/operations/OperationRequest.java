package com.sekomproject.sekom.util.operations;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.entities.TransactionType;
import lombok.Data;

@Data
public class OperationRequest {

    private Bank bank;
    private BankAccountOwner bankAccountOwner;
    private BankAccount bankAccount;
    private TransactionType transactionType;

}
