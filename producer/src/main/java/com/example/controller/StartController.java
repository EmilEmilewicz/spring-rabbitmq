package com.example.controller;

import com.example.entity.Command;
import com.example.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

        log.info("Started. Time -> {}.", System.currentTimeMillis());
        log.info("Sending messages...");

        IntStream.range(0, 100)
                .forEachOrdered(i -> commandService.sendCommand(createAndPersist(i)));

        log.info("finished");
    }

    private Command createAndPersist(int number) {

        Command command = new Command();
        command.setCapacity(ThreadLocalRandom.current().nextInt(3, 7));
        command.setNumber(number);

        return commandService.save(command);
    }
}
