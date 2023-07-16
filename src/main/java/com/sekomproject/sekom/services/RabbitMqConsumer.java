package com.sekomproject.sekom.services;

import com.sekomproject.sekom.entities.BankAccount;
import com.sekomproject.sekom.entities.TransactionType;
import com.sekomproject.sekom.util.operations.OperationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqConsumer {

    private final BankAccountService bankAccountService;

    @RabbitListener(queues = "${rabbitmq.routingkey}")
    public void receivedMessage(OperationRequest request) {
        BankAccount bankAccount;
        TransactionType operationType = request.getTransactionType();

        if (operationType.equals(TransactionType.DEPOSIT)) {
            System.out.println("RabbitMq deposit methods");
            bankAccountService.depositOperation(request);
        } else if (operationType.equals(TransactionType.WITHDRAWAL)) {
            bankAccountService.depositOperation(request);

        }
    }
}