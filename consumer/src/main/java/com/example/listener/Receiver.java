package com.example.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.config.RabbitConfig.QUEUE_MESSAGE;

@Component
public class Receiver {

    @RabbitListener(queues = QUEUE_MESSAGE)
    public void receiveMessage(String command) {
        System.out.println("Received <" + command + ">");
    }
}
