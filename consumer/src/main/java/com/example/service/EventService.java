package com.example.service;

import com.example.dto.Command;
import com.example.entity.Event;
import com.example.entity.EventType;
import com.example.exception.EventProcessingInterruptedException;
import com.example.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.example.entity.EventType.START;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event save(Event event) {

        return eventRepository.save(event);
    }

    public Event start(Command command) {

        return save(newEvent(command, START));
    }

    public Event finish(Event event) {

        event.setType(EventType.FINISH);

        return save(event);
    }

    public Event newEvent(Command cmd, EventType eventType) {

        Event event = new Event();
        event.setContext("context-" + cmd.getId());
        event.setTime(LocalDateTime.now());
        event.setType(eventType);

        return event;
    }

    public void process(Command cmd, Event event) {
        try {
            TimeUnit.SECONDS.sleep(cmd.getCapacity());
        } catch (InterruptedException e) {
            throw new EventProcessingInterruptedException("Command " + cmd + ", event + " + event);
        }
    }
}
