package com.sekomproject.sekom.services;


import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.entities.CommunicationInformation;
import com.sekomproject.sekom.repositories.CommunicationInformationRepository;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BankAccountOwnerService {

    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    @Transactional
    public BankAccountOwner createBankAccountOwner(BankAccountOwner bankAccountOwner) {

        String phoneNumber = bankAccountOwner.getCommunicationInformation().getPhoneNumber();
        String email = bankAccountOwner.getCommunicationInformation().getEmail();

        CommunicationInformation communicationInformation = new CommunicationInformation();

        communicationInformation.setBankAccountOwner(bankAccountOwner);
        communicationInformation.setEmail(email);
        communicationInformation.setPhoneNumber(phoneNumber);

        bankAccountOwner.setUniqueAccountOwnerNumber(UUID.randomUUID());
        bankAccountOwner.setCommunicationInformation(bankAccountOwner.getCommunicationInformation());
        return bankAccountOwnerRepository.save(bankAccountOwner);


    }

}
