package com.sekomproject.sekom.util.components;

import com.sekomproject.sekom.entities.*;
import com.sekomproject.sekom.repositories.BankAccountOwnerRepository;
import com.sekomproject.sekom.repositories.BankAccountRepository;
import com.sekomproject.sekom.repositories.BankRepository;
import com.sekomproject.sekom.repositories.TransactionRepository;
import com.sekomproject.sekom.util.operations.OperationRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
@RequiredArgsConstructor
public class TransactionLoggingAspectComponent {

    private final TransactionRepository transactionRepository;
    private final BankRepository bankRepository;
    private final BankAccountOwnerRepository bankAccountOwnerRepository;
    private final BankAccountRepository bankAccountRepository;

    @Pointcut("execution(* com.sekomproject.sekom.services.BankAccountService.withdrawalOperation(..)) && args(request) " +
            "|| execution(* com.sekomproject.sekom.services.BankAccountService.depositOperation(..)) && args(request)")
    public void bankServiceMethods(OperationRequest request) { }

    @Around("bankServiceMethods(request)")
    public Object aroundBankServiceMethods(ProceedingJoinPoint joinPoint, OperationRequest request) throws Throwable {
        System.out.println("Before executing method: " + joinPoint.getSignature());

        Object result = joinPoint.proceed();
        logging(request);
        try {
            System.out.println("After executing method: " + joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            System.out.println("Error executing method: " + joinPoint.getSignature());

            throw e;
        }
    }

    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logging(OperationRequest request) {
        BankAccount bankAccountFromRequest = request.getBankAccount();
        BankAccountOwner bankAccountOwnerFromRequest = request.getBankAccountOwner();
        Bank byBankName = bankRepository.findByBankName(request.getBank().getBankName()).get();
        BankAccount byAccountNumber = bankAccountRepository.findByAccountNumber(bankAccountFromRequest.getAccountNumber())
                .get();
        BankAccountOwner bankAccountOwner = bankAccountOwnerRepository
                .findByUniqueAccountOwnerNumber(bankAccountOwnerFromRequest.getUniqueAccountOwnerNumber()).get();

        TransactionType transactionType = request.getTransactionType();

        Transaction transaction = Transaction.builder()
                .type(transactionType)
                .bankAccount(byAccountNumber)
                .bank(byBankName)
                .bankAccountOwner(bankAccountOwner)
                .amount(request.getBankAccount().getBalance())
                .build();

        transactionRepository.save(transaction);
    }
}
