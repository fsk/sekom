package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankAccountRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.util.exceptions.*;
import com.sekomproject.sekom.util.operations.OperationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;
    public BankAccount createBankAccount(BankAccount bankAccount) {

        String bankName = bankAccount.getBank().getBankName();


        Bank bank = bankRepository
                .findByBankName(bankName).orElseThrow(() -> new BankNotFoundException(bankName));

        UUID accountUniqueNumber = bankAccount.getBankAccountOwner().getUniqueAccountOwnerNumber();
        Optional<BankAccountOwner> byUniqueAccountOwnerNumber = Optional.ofNullable(bankAccountOwnerRepository
                .findByUniqueAccountOwnerNumber(accountUniqueNumber)
                .orElseThrow(() -> new BankAccountOwnerNotFoundException(accountUniqueNumber)));

        bankAccount.setBalance(new BigDecimal(0));
        bankAccount.setBank(bank);
        bankAccount.setBankAccountOwner(byUniqueAccountOwnerNumber.get());
        bankAccount.setAccountNumber(generateAccountNumber(bankName));
        return bankAccountRepository.save(bankAccount);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankAccount withdrawalOperation(OperationRequest request) {
        accountAndBankValidation(request);
        BigDecimal balance = request.getBankAccount().getBalance();
        BigDecimal totalBalanceByOwnerUUID = getBigDecimal(request);
        if (totalBalanceByOwnerUUID.compareTo(balance) == -1) {
            throw new CannotWithdrawException(balance, totalBalanceByOwnerUUID);
        }
        BigDecimal newBalance = totalBalanceByOwnerUUID.subtract(balance);
        BankAccount bankAccount = request.getBankAccount();
        Optional<BankAccount> byAccountNumber = bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
        byAccountNumber.get().setBalance(newBalance);

        return bankAccountRepository.save(byAccountNumber.get());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankAccount depositOperation(OperationRequest request) {
        accountAndBankValidation(request);
        BigDecimal totalBalanceByOwnerUUID = getBigDecimal(request);
        BigDecimal newBalance = totalBalanceByOwnerUUID.add(request.getBankAccount().getBalance());

        BankAccount bankAccount = request.getBankAccount();

        Optional<BankAccount> byAccountNumber = bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
        byAccountNumber.get().setBalance(newBalance);

        return bankAccountRepository.save(byAccountNumber.get());

    }

    private BigDecimal getBigDecimal(OperationRequest request) {
        UUID uniqueAccountOwnerNumber = request.getBankAccountOwner().getUniqueAccountOwnerNumber();
        String bankName = request.getBank().getBankName();
        String accountNumber = request.getBankAccount().getAccountNumber();
        operationValidation(uniqueAccountOwnerNumber, bankName, accountNumber);
        return bankAccountRepository
                .getTotalBalanceByOwnerUUIDAndBankName(uniqueAccountOwnerNumber, bankName, accountNumber);
    }

    public List<Bank> bankListFromAccountOwner(String identityNumber) {

        return bankAccountOwnerRepository
                .findAllBanksByOwnerIdentityNumber(identityNumber);
    }

    private String generateAccountNumber(String getBankNameFromPayload) {
        StringBuilder res = new StringBuilder();
        Random rnd = new Random();
        res.append(getBankNameFromPayload, 0, 2);
        for (int i = 0; i < 20; i++) {
            res.append(rnd.nextInt(10));
        }
        return res.toString();
    }

    private void operationValidation(UUID uniqueAccountOwnerNumber, String bankName, String accountNumber) {

        bankAccountOwnerRepository
                .findByUniqueAccountOwnerNumber(uniqueAccountOwnerNumber)
                .orElseThrow(() -> new BankAccountOwnerNotFoundException(uniqueAccountOwnerNumber));

        bankRepository
                .findByBankName(bankName)
                .orElseThrow(() -> new BankAccountOwnerNotFoundException(bankName));

        bankAccountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException(accountNumber));

    }

    private void accountAndBankValidation(OperationRequest request) {

        String accountNumber = request.getBankAccount().getAccountNumber();
        String bankName = request.getBank().getBankName();

        if (!accountNumber.substring(0,2).equals(bankName.substring(0,2))) {
            throw new BankAndAccountNumberMatchException(bankName, accountNumber);
        }

    }



}
