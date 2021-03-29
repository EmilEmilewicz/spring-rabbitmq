package com.example.controller;

import com.example.entity.MessageEvent;
import com.example.service.CommandService;
import com.example.service.MessageEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@Slf4j
@RestController
@AllArgsConstructor
public class StartController {

    private final CommandService commandService;
    private final MessageEventService messageEventService;

    @GetMapping("/start")
    public ResponseEntity<String> start() {

        MessageEvent event = messageEventService.start();

        log.info("Started. Time -> {}.", event.getSendMessageToQueueStart());
        log.info("Sending messages... Time -> {}.", event.getSendMessageToQueueStart());

        IntStream.range(0, 100)
                .forEachOrdered(commandService::sendCommand);

        event = messageEventService.finish(event);
        log.info("Finished. Time -> {}", event.getSendMessageToQueueFinish());

        return ResponseEntity.ok("Success!!!");
    }
}
