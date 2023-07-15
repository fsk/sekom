package com.sekomproject.sekom.dto;

import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccount;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDto {

    String bankAccountNumber;
    BankDto bankInfo;
    BigDecimal balance;
    public BankAccountDto convertToDTO(BankAccount bankAccount) {
        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setBankAccountNumber(bankAccount.getAccountNumber());
        bankAccountDto.setBankInfo(convertToBankDto(bankAccount.getBank()));
        bankAccountDto.setBalance(bankAccount.getBalance());
        return bankAccountDto;
    }

    private BankDto convertToBankDto(Bank bank) {
        BankDto bankDto = new BankDto();
        return bankDto.convertToDTO(bank);
    }

}
