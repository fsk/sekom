package com.sekomproject.sekom.services;

import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountOwnerRepository bankAccountOwnerRepository;
}
