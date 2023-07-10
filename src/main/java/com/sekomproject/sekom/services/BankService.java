package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.repositories.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;


    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

}
