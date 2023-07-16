package com.sekomproject.sekom.services;

import com.sekomproject.sekom.util.operations.OperationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingkey;

    public RabbitMqSender(RabbitTemplate rabbitTemplate,
                          @Value("${rabbitmq.exchange}") String exchange,
                          @Value("${rabbitmq.routingkey}") String routingkey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingkey = routingkey;
    }

    public void send(OperationRequest request) {
        rabbitTemplate.convertAndSend(exchange, routingkey, request);
    }
}
