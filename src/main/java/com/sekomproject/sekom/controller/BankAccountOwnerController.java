package com.sekomproject.sekom.controller;

import com.sekomproject.sekom.services.BankAccountOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bankAccountOwner")
@RequiredArgsConstructor
public class BankAccountOwnerController {

    private final BankAccountOwnerService bankAccountOwnerService;
}
