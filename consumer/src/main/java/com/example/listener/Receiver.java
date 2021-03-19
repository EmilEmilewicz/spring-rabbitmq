package com.example.listener;

import com.example.dto.Command;
import com.example.entity.Event;
import com.example.entity.EventType;
import com.example.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.config.RabbitConfig.QUEUE_MESSAGE;
import static com.example.entity.EventType.FINISH;
import static com.example.entity.EventType.START;

@Slf4j
@Component
public class Receiver {

    private final ObjectMapper mapper;
    private final EventService eventService;

    public Receiver(ObjectMapper mapper, EventService eventService) {
        this.mapper = mapper;
        this.eventService = eventService;
    }

    @RabbitListener(queues = QUEUE_MESSAGE)

    public void receiveMessage(String command) {

        Command cmd = toCommand(command);

        if (!Objects.isNull(cmd)) {

            Event start = eventService.save(newEvent(cmd, START));

            log.info("start -> {}", start);

            sleep(cmd);

            Event finish = eventService.save(newEvent(cmd, FINISH));

            log.info("finish -> {}", finish);
        }
    }

    private void sleep(Command cmd) {
        try {
            Thread.sleep(cmd.getCapacity() * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    private Command toCommand(String command) {
        try {
            return mapper.readValue(command, Command.class);
        } catch (JsonProcessingException ignored) {
        }
        return null;
    }

    private Event newEvent(Command cmd, EventType eventType) {

        Event event = new Event();
        event.setContext("context-" + cmd.getId());
        event.setTime(LocalDateTime.now());
        event.setType(eventType);

        return event;
    }
}
