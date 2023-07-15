package com.sekomproject.sekom.controller;

import com.sekomproject.sekom.dto.BankAccountOwnerDto;
import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.services.BankAccountOwnerService;
import com.sekomproject.sekom.services.BankAccountService;
import com.sekomproject.sekom.util.Response;
import com.sekomproject.sekom.util.SuccessMessages;
import com.sekomproject.sekom.util.exceptions.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/bankAccountOwner")
@RequiredArgsConstructor
public class BankAccountOwnerController {

    private final BankAccountOwnerService bankAccountOwnerService;
    private final BankAccountService bankAccountService;

    @PostMapping("/")
    public Response<BankAccountOwnerDto> createBankAccountOwner(@RequestBody BankAccountOwner bankAccountOwner) {
        BankAccountOwner newBankAccountOwner = bankAccountOwnerService.createBankAccountOwner(bankAccountOwner);
        return new Response<>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                SuccessMessages.CREATE_NEW_BANK_OWNER,
                new BankAccountOwnerDto().convertToDTO(newBankAccountOwner));
    }

     @GetMapping("/{identityNumber}")
    public Response<?> getAllBanksFromUniqueAccountOwnerNumber(@PathVariable("identityNumber") String identityNumber) {
        List<Bank> banks = bankAccountService.bankListFromAccountOwner(identityNumber);
        boolean res = banks.isEmpty();
        return new Response<>(
                res ? HttpStatus.BAD_REQUEST : HttpStatus.OK,
                res ? HttpStatus.BAD_REQUEST.value() : HttpStatus.OK.value(),
                res ? ErrorMessages.BANK_ACCOUNT_OWNER_NOT_FOUND : SuccessMessages.ALL_BANKS_FROM_ACCOUNTS_OWNER,
                res ? null : banks
        );
    }

}
