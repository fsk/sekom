package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.entities.Transaction;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankAccountRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.repositories.TransactionRepository;
import com.sekomproject.sekom.util.exceptions.BankAccountNotFoundException;
import com.sekomproject.sekom.util.exceptions.BankAccountOwnerNotFoundException;
import com.sekomproject.sekom.util.exceptions.BankNotFoundException;
import com.sekomproject.sekom.util.operations.OperationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankRepository bankRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountOwnerRepository bankAccountOwnerRepository;

    public List<Transaction> getTransactionsFromDate(OperationRequest request) {

        String bankName = request.getBank().getBankName();
        String accountNumber = request.getBankAccount().getAccountNumber();
        UUID uniqueAccountOwnerNumber = request.getBankAccountOwner().getUniqueAccountOwnerNumber();

        validationRequests(bankName, accountNumber, uniqueAccountOwnerNumber);


        BankAccount bankAccount = bankAccountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException(accountNumber));

        Bank byBankName = bankRepository
                .findByBankName(bankName)
                .orElseThrow(() -> new BankAccountNotFoundException(bankName));

        BankAccountOwner bankAccountOwner = bankAccountOwnerRepository
                .findByUniqueAccountOwnerNumber(uniqueAccountOwnerNumber)
                .orElseThrow(() -> new BankAccountOwnerNotFoundException(uniqueAccountOwnerNumber));

        return transactionRepository
                .findAllByOperations(bankAccount.getId(), byBankName.getId(), bankAccountOwner.getId(), request.getDate());

    }

    private void validationRequests(String bankName, String accountNumber, UUID uniqueAccountOwnerNumber) {
        bankAccountOwnerRepository
                .findByUniqueAccountOwnerNumber(uniqueAccountOwnerNumber)
                .orElseThrow(() -> new BankAccountOwnerNotFoundException(uniqueAccountOwnerNumber));

        bankRepository
                .findByBankName(bankName)
                .orElseThrow(() -> new BankNotFoundException(bankName));

        bankAccountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException(accountNumber));
    }
}
