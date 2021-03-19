package com.example.controller;

import com.example.entity.Command;
import com.example.exception.CommandToJsonException;
import com.example.service.CommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.example.config.RabbitConfig.QUEUE_MESSAGE;
import static com.example.config.RabbitConfig.TOPIC_EXCHANGE_NAME;

@Slf4j
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

        log.info("Started. Time -> {}.", System.currentTimeMillis());
        log.info("Sending messages...");

        IntStream.range(0, 100)
                .forEachOrdered(i -> sendCommand(createAndPersist(i)));

        log.info("finished");
    }

    private Command createAndPersist(int number) {

        Command command = new Command();
        command.setCapacity(ThreadLocalRandom.current().nextInt(3, 7));
        command.setNumber(number);

        return commandService.save(command);
    }

    private void sendCommand(Command command) {
        try {
            String commandJson = objectMapper.writeValueAsString(command);
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, QUEUE_MESSAGE, commandJson);

            log.info("{}", commandJson);

        } catch (JsonProcessingException e) {
            throw new CommandToJsonException(e.getMessage());
        }
    }
}
