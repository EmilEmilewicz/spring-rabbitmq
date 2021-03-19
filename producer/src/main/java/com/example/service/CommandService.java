package com.example.service;

import com.example.entity.Command;
import com.example.exception.CommandToJsonException;
import com.example.repository.CommandRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.config.RabbitConfig.QUEUE_MESSAGE;
import static com.example.config.RabbitConfig.TOPIC_EXCHANGE_NAME;

@Slf4j
@Service
public class CommandService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final CommandRepository commandRepository;

    public CommandService(RabbitTemplate rabbitTemplate,
                          ObjectMapper objectMapper,
                          CommandRepository commandRepository) {

        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.commandRepository = commandRepository;
    }

    public Command save(Command command) {

        return commandRepository.save(command);
    }

    public void sendCommand(Command command) {

        try {
            String commandJson = objectMapper.writeValueAsString(command);
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, QUEUE_MESSAGE, commandJson);

            log.info("{}", commandJson);

        } catch (JsonProcessingException e) {
            throw new CommandToJsonException(e.getMessage());
        }
    }
}
