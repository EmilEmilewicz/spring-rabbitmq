package com.example.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "producer_message_event")
@Builder
public class MessageEvent {

    /**
     * Id with auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Fixating start time of the sending commands
     * to the RabbitMQ Queue
     */
    @Column(name = "SEND_MESSAGES_TO_QUEUE_START")
    private LocalDateTime sendMessageToQueueStart;
    /**
     * Fixating finish time of the sending commands
     * to the RabbitMQ Queue
     */
    @Column(name = "SEND_MESSAGES_TO_QUEUE_FINISH")
    private LocalDateTime sendMessageToQueueFinish;
}
