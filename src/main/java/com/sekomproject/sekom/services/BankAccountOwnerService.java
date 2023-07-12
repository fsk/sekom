package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.util.exceptions.BankNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountOwnerService {

    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    private final BankRepository bankRepository;

    @Transactional
    public BankAccountOwner createBankAccountOwner(BankAccountOwner bankAccountOwner) {

        Set<Bank> banks = bankAccountOwner.getBanks().stream().map(bank -> {
            String bankName = bank.getBankName();
            Bank existingBank = bankRepository.findByBankName(bankName)
                    .orElseThrow(() -> new BankNotFoundException(bankName));
            existingBank.getBankAccountOwners().add(bankAccountOwner);
            //bankRepository.saveAndFlush(existingBank);
            return existingBank;
        }).collect(Collectors.toSet());

        bankAccountOwner.setBanks(banks);
        BankAccountOwner savedBankAccountOwner = bankAccountOwnerRepository.save(bankAccountOwner);
        banks.forEach(bank -> {
            bank.getBankAccountOwners().add(savedBankAccountOwner);
            bankRepository.save(bank);
        });
        return savedBankAccountOwner;
    }

}
