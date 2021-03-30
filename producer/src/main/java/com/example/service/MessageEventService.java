package com.example.service;

import com.example.entity.MessageEvent;
import com.example.repository.MessageEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MessageEventService {

    private final MessageEventRepository messageEventRepository;

    /**
     * Creates new Message event,
     * sets the start time and stores to the database
     *
     * @return newly created and stored to the DB MessageEvent
     */
    public MessageEvent start() {
        return messageEventRepository.save(
                MessageEvent.builder()
                        .sendMessageToQueueStart(LocalDateTime.now())
                        .build());
    }

    /**
     * Updates finish time of the MessageEvent
     *
     * @param event which should be finished and persisted
     * @return updated MessageEvent
     */
    public MessageEvent finish(MessageEvent event) {

        event.setSendMessageToQueueFinish(LocalDateTime.now());

        return messageEventRepository.save(event);
    }
}
