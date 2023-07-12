package com.sekomproject.sekom.controller;

import com.sekomproject.sekom.dto.BankAccountOwnerDto;
import com.sekomproject.sekom.entities.BankAccountOwner;
import com.sekomproject.sekom.services.BankAccountOwnerService;
import com.sekomproject.sekom.util.Response;
import com.sekomproject.sekom.util.SuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bankAccountOwner")
@RequiredArgsConstructor
public class BankAccountOwnerController {

    private final BankAccountOwnerService bankAccountOwnerService;

    @PostMapping("/")
    public Response<BankAccountOwnerDto> createBankAccountOwner(@RequestBody BankAccountOwner bankAccountOwner) {
        BankAccountOwner newBankAccountOwner = bankAccountOwnerService.createBankAccountOwner(bankAccountOwner);
        return new Response<>(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                SuccessMessages.CREATE_NEW_BANK_OWNER,
                new BankAccountOwnerDto().convertToDTO(newBankAccountOwner));
    }

}
