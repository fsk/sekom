package com.sekomproject.sekom.util.components;

import com.sekomproject.sekom.entities.*;
import com.sekomproject.sekom.repositories.TransactionRepository;
import com.sekomproject.sekom.util.operations.OperationRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


//@Component
//@Aspect
@RequiredArgsConstructor
public class TransactionLoggingAspectComponent {

    private final TransactionRepository transactionRepository;


    @Pointcut("execution(* com.sekomproject.sekom.services.BankAccountService.withdrawalOperation(..)) && args(request) " +
            "|| execution(* com.sekomproject.sekom.services.BankAccountService.depositOperation(..)) && args(request)")
    public void bankServiceMethods(OperationRequest request) { }

    @Around("bankServiceMethods(request)")
    public Object aroundBankServiceMethods(ProceedingJoinPoint joinPoint, OperationRequest request) throws Throwable {
        System.out.println("Before executing method: " + joinPoint.getSignature());

        Bank bankFromRequest = request.getBank();
        BankAccount bankAccountFromRequest = request.getBankAccount();
        BankAccountOwner bankAccountOwnerFromRequest = request.getBankAccountOwner();

        TransactionType transactionType = request.getTransactionType();


        Transaction transaction = Transaction.builder()
                .type(transactionType)
                .bankAccount(bankAccountFromRequest)
                .bank(bankFromRequest)
                .bankAccountOwner(bankAccountOwnerFromRequest)
                .build();

        // Transaction tablonuzda bir satır oluşturun ve transaction ID'sini oraya kaydedin
        transactionRepository.save(transaction);

        try {
            // İşlem devam ederken...
            Object result = joinPoint.proceed();

            // İşlem tamamlandıktan sonra...
            System.out.println("After executing method: " + joinPoint.getSignature());

            return result;
        } catch (Exception e) {
            // Hata durumunda...
            System.out.println("Error executing method: " + joinPoint.getSignature());

            throw e;
        }
    }
}
