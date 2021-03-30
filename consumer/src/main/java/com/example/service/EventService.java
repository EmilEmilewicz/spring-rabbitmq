package com.example.service;

import com.example.dto.Command;
import com.example.entity.Event;
import com.example.entity.EventType;
import com.example.exception.EventProcessingInterruptedException;
import com.example.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.entity.EventType.FINISH;
import static com.example.entity.EventType.START;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    /**
     * Saving an event to the DB
     *
     * @param event which should be stored
     * @return stored Event
     */
    public Event save(Event event) {

        return eventRepository.save(event);
    }

    /**
     * Creating new event with type START
     * and saving it in DB
     *
     * @param command for the event that should be created
     * @return newly created and stored Event
     */
    public Event start(Command command) {

        return save(newEvent(command.getId(), START));
    }

    /**
     * Creating new event with type FINISH
     * and saving it in DB
     *
     * @param command for the event that should be created
     * @return newly created and stored Event
     */
    public Event finish(Command command) {

        return save(newEvent(command.getId(), FINISH));
    }

    /**
     * @param commandId for which new event will be created
     * @param eventType START|FINISH
     * @return new instantiated Event
     */
    public Event newEvent(UUID commandId, EventType eventType) {

        Event event = new Event();
        event.setContext(commandId);
        event.setTime(LocalDateTime.now());
        event.setType(eventType);

        return event;
    }

    /**
     * Processing an event according its capacity
     *
     * @param command that should be processed
     * @param event   needed to show which was interrupted
     */
    public void process(Command command, Event event) {
        try {
            TimeUnit.SECONDS.sleep(command.getCapacity());
        } catch (InterruptedException e) {
            throw new EventProcessingInterruptedException(
                    "Command " + command + ", event + " + event);
        }
    }
}
