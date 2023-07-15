package com.sekomproject.sekom.services;


import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.entities.CommunicationInformation;
import com.sekomproject.sekom.repositories.CommunicationInformationRepository;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.util.exceptions.EmailValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class BankAccountOwnerService {

    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    @Transactional
    public BankAccountOwner createBankAccountOwner(BankAccountOwner bankAccountOwner) {

        String phoneNumber = bankAccountOwner.getCommunicationInformation().getPhoneNumber();
        String email = bankAccountOwner.getCommunicationInformation().getEmail();

        emailValidation(email);

        CommunicationInformation communicationInformation = new CommunicationInformation();

        communicationInformation.setBankAccountOwner(bankAccountOwner);
        communicationInformation.setEmail(email);
        communicationInformation.setPhoneNumber(phoneNumber);

        bankAccountOwner.setUniqueAccountOwnerNumber(UUID.randomUUID());
        bankAccountOwner.setCommunicationInformation(bankAccountOwner.getCommunicationInformation());
        return bankAccountOwnerRepository.save(bankAccountOwner);


    }

    private void emailValidation(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            throw new EmailValidationException(email);
        }
    }

}
