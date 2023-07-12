package com.sekomproject.sekom.dto;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccountOwner;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BankAccountOwnerDto {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private List<BankDto> bankDtos;

    public BankAccountOwnerDto convertToDTO(BankAccountOwner bankAccountOwner) {
        BankAccountOwnerDto dto = new BankAccountOwnerDto();
        dto.setName(bankAccountOwner.getFirstName());
        dto.setSurname(bankAccountOwner.getLastName());
        dto.setEmail(bankAccountOwner.getEmail());
        dto.setPhoneNumber(bankAccountOwner.getPhoneNumber());
        dto.setBankDtos(bankAccountOwner.getBanks().stream()
                .map(this::convertToBankDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private BankDto convertToBankDto(Bank bank) {
        BankDto bankDto = new BankDto();
        bankDto.convertToDTO(bank);
        return bankDto;
    }
}
