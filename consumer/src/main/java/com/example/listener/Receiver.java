package com.example.listener;

import com.example.dto.Command;
import com.example.entity.Event;
import com.example.service.CommandService;
import com.example.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class Receiver {

    private final EventService eventService;
    private final CommandService commandService;

    @RabbitListener(queues = "${rabbit.queue.message}")
    public void receiveMessage(String command) {

        log.info("Received command {}.", command);

        Command cmd = commandService.toCommand(command);

        Event event = eventService.start(cmd);

        log.info("Event started. Event id - {}, type - {}, time - {}, context - {}.",
                event.getId(), event.getType(), event.getTime(), event.getContext());

        log.info("Event processing...");

        eventService.process(cmd, event);

        event = eventService.finish(cmd);

        log.info("Event finished. Event id - {}, type - {}, time - {}, context - {}.",
                event.getId(), event.getType(), event.getTime(), event.getContext());
    }
}
