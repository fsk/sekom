package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankAccountRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.util.exceptions.BankAccountOwnerNotFoundException;
import com.sekomproject.sekom.util.exceptions.BankNotFoundException;
import com.sekomproject.sekom.util.exceptions.MissingFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;
    public BankAccount createBankAccount(BankAccount bankAccount) {

        Supplier<Stream<Bank>> bankAccountStream = () -> bankAccount
                .getBankAccountOwner()
                .getBanks()
                .stream();

        String getBankNameFromPayload = bankAccountStream
                .get()
                .map(Bank::getBankName)
                .findFirst()
                .orElseThrow(MissingFieldException::new);


        Bank bank = bankRepository
                .findByBankName(getBankNameFromPayload).orElseThrow(() -> new BankNotFoundException(getBankNameFromPayload));

        UUID accountUniqueNumber = bankAccount.getBankAccountOwner().getUniqueAccountOwnerNumber();
        Optional<BankAccountOwner> byUniqueAccountOwnerNumber = Optional.ofNullable(bankAccountOwnerRepository
                .findByUniqueAccountOwnerNumber(accountUniqueNumber)
                .orElseThrow(() -> new BankAccountOwnerNotFoundException(accountUniqueNumber.toString())));

        bankAccount.setBank(bank);
        bankAccount.setBankAccountOwner(byUniqueAccountOwnerNumber.get());
        bankAccount.setAccountNumber(generateAccountNumber(getBankNameFromPayload));
        return bankAccountRepository.save(bankAccount);
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
