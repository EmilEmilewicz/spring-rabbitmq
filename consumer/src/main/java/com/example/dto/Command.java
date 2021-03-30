package com.example.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Command {

    /**
     * Randomly generated id. Format UUID
     */
    private UUID id;
    /**
     * Sequence number of the Command
     */
    private Integer number;
    /**
     * Indicates process time of the Command.
     * The range is between 3 and 7 seconds
     */
    private Integer capacity;
}
