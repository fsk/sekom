package com.sekomproject.sekom.controller;


import com.sekomproject.sekom.dto.BankAccountDto;
import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.services.BankAccountService;
import com.sekomproject.sekom.util.Response;
import com.sekomproject.sekom.util.SuccessMessages;
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

    @PostMapping("/")
    public Response<BankAccountDto> createBankAccount(@RequestBody BankAccount bankAccount) {
        BankAccount newBankAccount = bankAccountService.createBankAccount(bankAccount);
        return new Response<>(HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                SuccessMessages.CREATE_NEW_BANK,
                new BankAccountDto().convertToDTO(newBankAccount));
    }
}
