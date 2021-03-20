package com.example.controller;

import com.example.entity.Command;
import com.example.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Slf4j
@RestController
public class StartController {

    private final CommandService commandService;

    public StartController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping("/start")
    public void start() {

        log.info("Started. Time -> {}.", LocalDateTime.now());
        log.info("Sending messages... Time -> {}.", LocalDateTime.now());

        IntStream.range(0, 100)
                .forEachOrdered(i -> commandService.sendCommand(createAndPersist(i)));

        log.info("Finished. Time -> {}", LocalDateTime.now());
    }

    private Command createAndPersist(int number) {

        Command command = new Command();
        command.setCapacity(ThreadLocalRandom.current().nextInt(3, 7));
        command.setNumber(number);

        return commandService.save(command);
    }
}
