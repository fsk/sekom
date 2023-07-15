package com.sekomproject.sekom.controller;

import com.sekomproject.sekom.dto.BankDto;
import com.sekomproject.sekom.entities.Bank;
import com.sekomproject.sekom.services.BankService;
import com.sekomproject.sekom.util.Response;
import com.sekomproject.sekom.util.SuccessMessages;
import com.sekomproject.sekom.util.exceptions.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @PostMapping("/")
    public Response<BankDto> createBank(@RequestBody Bank bank) {
        Bank newBank = bankService.createBank(bank);
        return new Response<>(HttpStatus.CREATED,
                              HttpStatus.CREATED.value(),
                              SuccessMessages.CREATE_NEW_BANK,
                              new BankDto().convertToDTO(newBank));
    }

    @DeleteMapping("/{bankName}")
    public Response<?> deleteBank(@PathVariable(value = "bankName") String bankName) {
        bankService.deleteBank(bankName);
        return new Response<>(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                SuccessMessages.DELETE_BANK
                );
    }

//    @GetMapping("/{uuid}")
//    public Response<?> getAllBanksFromUniqueAccountOwnerNumber(@PathVariable("uuid") UUID uniqueAccountOwnerNumber) {
//        List<Bank> banks = bankService.bankListFromAccountOwner(uniqueAccountOwnerNumber);
//        boolean res = banks.isEmpty();
//        return new Response<>(
//                res ? HttpStatus.BAD_REQUEST : HttpStatus.OK,
//                res ? HttpStatus.BAD_REQUEST.value() : HttpStatus.OK.value(),
//                res ? ErrorMessages.BANK_ACCOUNT_OWNER_NOT_FOUND : SuccessMessages.ALL_BANKS_FROM_ACCOUNTS_OWNER,
//                res ? null : banks
//        );
//    }

}
