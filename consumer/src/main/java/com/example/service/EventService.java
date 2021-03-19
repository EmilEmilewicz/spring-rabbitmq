package com.example.service;

import com.example.entity.Event;
import com.example.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {

        return eventRepository.save(event);
    }
}
