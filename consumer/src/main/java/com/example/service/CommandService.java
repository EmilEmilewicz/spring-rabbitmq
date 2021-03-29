package com.example.service;

import com.example.dto.Command;
import com.example.exception.InvalidCommandException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandService {

    private final ObjectMapper mapper;

    public Command toCommand(String command) {
        try {
            return mapper.readValue(command, Command.class);
        } catch (JsonProcessingException ignored) {
            throw new InvalidCommandException("Command -> `" + command + "` is not valid.");
        }
    }
}
