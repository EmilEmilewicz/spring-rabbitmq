package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "command")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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