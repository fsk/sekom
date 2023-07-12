package com.sekomproject.sekom.dto;

import com.sekomproject.sekom.entities.Bank;
import lombok.Data;

@Data
public class BankDto {
    String bankName;

    public static BankDto convertToDTO(Bank bank) {
        BankDto dto = new BankDto();
        dto.setBankName(bank.getBankName());
        return dto;
    }
}