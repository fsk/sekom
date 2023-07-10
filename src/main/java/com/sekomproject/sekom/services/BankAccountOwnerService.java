package com.sekomproject.sekom.services;

import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountOwnerService {

    private final BankAccountOwnerRepository bankAccountOwnerRepository;

}
