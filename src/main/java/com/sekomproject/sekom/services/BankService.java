package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.util.Response;
import com.sekomproject.sekom.util.exceptions.BankNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;


    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public void deleteBank(String bankName) {
        Bank bank = bankRepository.findByBankName(bankName).orElseThrow(() -> new BankNotFoundException(bankName));
        bankRepository.deleteById(bank.getId());
    }

    public List<Bank> bankListFromAccountOwner(UUID uniqueAccountOwnerNumber) {

        return bankRepository
                .findByBankAccountOwnersUniqueAccountOwnerNumber(uniqueAccountOwnerNumber);

    }
}
