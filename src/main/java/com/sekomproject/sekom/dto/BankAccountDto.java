package com.sekomproject.sekom.dto;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.BankAccountOwner;
import lombok.Data;

@Data
public class BankAccountDto {

    String bankAccountNumber;
    BankDto bankInfo;
    public BankAccountDto convertToDTO(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setBankAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setBankInfo(convertToBankDto(bankAccount.getBank()));
        return bankAccountDto;
    }

    private BankDto convertToBankDto(Bank bank) {
        BankDto bankDto = new BankDto();
        return bankDto.convertToDTO(bank);
    }

}
