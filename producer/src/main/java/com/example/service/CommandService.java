package com.example.service;

import com.example.entity.Command;
import com.example.exception.CommandToJsonException;
import com.example.repository.CommandRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class CommandService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommandRepository commandRepository;
    @Value("${rabbit.topic.message}")
    private String topicName;
    @Value("${rabbit.queue.message}")
    private String queueMessage;

    /**
     * Saving an Command to the DB
     *
     * @param command the one should be saved
     * @return saved to the DB Command
     */
    public Command save(Command command) {

        return commandRepository.save(command);
    }

    /**
     * Sending command to the RabbitMQ
     *
     * @param commandNumber sequential number of the Command
     */
    public void sendCommand(int commandNumber) {

        Command cmd = createAndPersist(commandNumber);
        String commandJson = toJson(cmd);

        rabbitTemplate.convertAndSend(topicName, queueMessage, commandJson);

        log.info("Command -> {} sent.", commandJson);
    }

    /**
     * Converts Command to the JSON String format
     *
     * @param command that should converted to String
     * @return Command JSON String format
     */
    private String toJson(Command command) {

        try {
            return objectMapper.writeValueAsString(command);
        } catch (JsonProcessingException e) {
            throw new CommandToJsonException(e.getMessage());
        }
    }

    /**
     * Creating and persisting Command to the DB
     *
     * @param commandNumber
     * @return Command that stored in DB after creation
     */
    public Command createAndPersist(int commandNumber) {

        Command command = new Command();
        command.setCapacity(ThreadLocalRandom.current().nextInt(3, 7));
        command.setNumber(commandNumber);

        return save(command);
    }
}
