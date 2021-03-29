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

    public MessageEvent start() {
        return messageEventRepository.save(
                MessageEvent.builder()
                        .sendMessageToQueueStart(LocalDateTime.now())
                        .build());
    }

    public MessageEvent finish(MessageEvent event) {

        event.setSendMessageToQueueFinish(LocalDateTime.now());

        return messageEventRepository.save(event);
    }
}
