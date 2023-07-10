package com.sekomproject.sekom.controller;


import com.sekomproject.sekom.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bankAccount")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
}
