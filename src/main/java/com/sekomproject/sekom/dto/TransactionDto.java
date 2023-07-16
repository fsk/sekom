package com.sekomproject.sekom.dto;

import com.sekomproject.sekom.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    BigDecimal balance;
    String createdDate;

    public List<TransactionDto> convertToDTO(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> new TransactionDto(transaction.getAmount(), transaction.getCreationDate().toString()))
                .collect(Collectors.toList());
    }


}
