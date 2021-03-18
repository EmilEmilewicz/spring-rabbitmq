package com.example.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Command {

    private UUID id;
    private Long number;
    private Integer capacity;
}
