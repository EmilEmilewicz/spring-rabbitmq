package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "event")
public class Event {

    /**
     * Randomly generated id. Format UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    /**
     * Event type START | FINISH
     */
    private EventType type;
    /**
     * Time of creation of the event
     */
    private LocalDateTime time;
    /**
     * It is command id.
     */
    private UUID context;
}
