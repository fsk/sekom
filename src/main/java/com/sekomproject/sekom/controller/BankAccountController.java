package com.sekomproject.sekom.controller;


import com.sekomproject.sekom.dto.BankAccountDto;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.TransactionType;
import com.sekomproject.sekom.services.BankAccountService;
import com.sekomproject.sekom.services.RabbitMqSender;
import com.sekomproject.sekom.util.Response;
import com.sekomproject.sekom.util.SuccessMessages;
import com.sekomproject.sekom.util.operations.OperationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bankAccount")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final RabbitMqSender rabbitMqSender;

    @PostMapping("/")
    public Response<BankAccountDto> createBankAccount(@RequestBody BankAccount bankAccount) {
        BankAccount newBankAccount = bankAccountService.createBankAccount(bankAccount);
        return new Response<>(HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                SuccessMessages.CREATE_NEW_BANK,
                new BankAccountDto().convertToDTO(newBankAccount));
    }

    @PostMapping("/operation")
    public Response<?> postOperation(@RequestBody OperationRequest request) {
        BankAccount bankAccount;
        TransactionType operationTpe = request.getTransactionType();
        rabbitMqSender.send(request);
        if (operationTpe.equals(TransactionType.DEPOSIT)) {
            bankAccount = bankAccountService.depositOperation(request);
            return new Response<>(
                    HttpStatus.OK,
                    HttpStatus.OK.value(),
                    SuccessMessages.WITHDRAW,
                    new BankAccountDto().convertToDTO(bankAccount)
                    );
        } else if (operationTpe.equals(TransactionType.WITHDRAWAL)) {
            bankAccount = bankAccountService.depositOperation(request);
        } else {

        }
        return new Response<>();
    }
}
