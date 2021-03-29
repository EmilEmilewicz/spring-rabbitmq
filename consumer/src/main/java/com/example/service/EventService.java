package com.example.service;

import com.example.entity.Event;
import com.example.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event save(Event event) {

        return eventRepository.save(event);
    }
}
