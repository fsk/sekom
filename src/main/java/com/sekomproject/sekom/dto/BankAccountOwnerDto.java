package com.sekomproject.sekom.dto;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccountOwner;
import lombok.Data;


import java.util.UUID;


@Data
public class BankAccountOwnerDto {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private UUID uniqueBankAccountOwner;
    private Object bankAccount;

    public BankAccountOwnerDto convertToDTO(BankAccountOwner bankAccountOwner) {
        BankAccountOwnerDto dto = new BankAccountOwnerDto();

        dto.setName(bankAccountOwner.getFirstName());
        dto.setSurname(bankAccountOwner.getLastName());
        dto.setEmail(bankAccountOwner.getCommunicationInformation().getEmail());
        dto.setPhoneNumber(bankAccountOwner.getCommunicationInformation().getPhoneNumber());
        dto.setUniqueBankAccountOwner(bankAccountOwner.getUniqueAccountOwnerNumber());
        dto.setBankAccount(bankAccountOwner.getAccounts().isEmpty() ? "Bilgi yok" : bankAccountOwner.getAccounts());

        return dto;
    }

    private BankDto convertToBankDto(Bank bank) {
        BankDto bankDto = new BankDto();
        return bankDto.convertToDTO(bank);
    }
}
