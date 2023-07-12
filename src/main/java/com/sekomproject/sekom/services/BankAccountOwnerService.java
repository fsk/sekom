package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.util.exceptions.BankNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
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
            return existingBank;
        }).collect(Collectors.toSet());

        bankAccountOwner.setBanks(banks);
        bankAccountOwner.setUniqueAccountOwnerNumber(UUID.randomUUID());
        return bankAccountOwnerRepository.save(bankAccountOwner);

    }

}
