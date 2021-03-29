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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "SEND_MESSAGES_TO_QUEUE_START")
    private LocalDateTime sendMessageToQueueStart;
    @Column(name = "SEND_MESSAGES_TO_QUEUE_FINISH")
    private LocalDateTime sendMessageToQueueFinish;
}
