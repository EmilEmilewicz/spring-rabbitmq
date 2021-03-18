package com.example.controller;

import com.example.entity.Command;
import com.example.service.CommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.config.RabbitConfig.QUEUE_MESSAGE;
import static com.example.config.RabbitConfig.TOPIC_EXCHANGE_NAME;

@RestController
public class StartController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommandService commandService;

    @GetMapping("/start")
    public void start() {

        System.out.println("started");
        System.out.println("Sending message...");

        for (long i = 0; i < 100; i++) {

            Command command = new Command();;
            command.setCapacity(ThreadLocalRandom.current().nextInt(3, 7));
            command.setNumber(i);

            Command saveCommand = commandService.save(command);

            try {

                String commandJson = objectMapper.writeValueAsString(saveCommand);
                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, QUEUE_MESSAGE, commandJson);

                sleep(command.getCapacity());

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        System.out.println("finished");
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException ignored) {
        }
    }
}
