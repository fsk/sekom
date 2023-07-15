package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankAccountRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.util.exceptions.BankAccountOwnerNotFoundException;
import com.sekomproject.sekom.util.exceptions.BankNotFoundException;
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

    public BankAccount withdrawalOperation(OperationRequest request) {
        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankAccount depositOperation(OperationRequest request) {
        UUID uniqueAccountOwnerNumber = request.getBankAccountOwner().getUniqueAccountOwnerNumber();
        BigDecimal totalBalanceByOwnerUUID = bankAccountRepository.getTotalBalanceByOwnerUUIDAndBankName(uniqueAccountOwnerNumber, request.getBank().getBankName());
        BigDecimal newBalance = totalBalanceByOwnerUUID.multiply(request.getBankAccount().getBalance());

        BankAccount bankAccount = request.getBankAccount();
        bankAccount.setBalance(newBalance);

        return bankAccountRepository.save(bankAccount);

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



}
