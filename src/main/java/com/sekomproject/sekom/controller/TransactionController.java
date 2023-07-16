package com.sekomproject.sekom.controller;

import com.sekomproject.sekom.dto.TransactionDto;
import com.sekomproject.sekom.entities.Transaction;
import com.sekomproject.sekom.entities.TransactionType;
import com.sekomproject.sekom.services.TransactionService;
import com.sekomproject.sekom.util.Response;
import com.sekomproject.sekom.util.SuccessMessages;
import com.sekomproject.sekom.util.operations.OperationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/operationsDate")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/")
    public Response<List<TransactionDto>> getOperations(@RequestBody OperationRequest request) {
        List<Transaction> transactionList = transactionService.getTransactionsFromDate(request);
        return new Response<>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                request.getTransactionType().equals(TransactionType.WITHDRAWAL) ? SuccessMessages.WITHDRAWAL_HISTORY : SuccessMessages.DEPOSIT_HISTORY,
                new TransactionDto().convertToDTO(transactionList)
        );
    }

}
