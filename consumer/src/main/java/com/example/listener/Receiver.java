package com.example.listener;

import com.example.dto.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.config.RabbitConfig.QUEUE_MESSAGE;

@Component
public class Receiver {

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = QUEUE_MESSAGE)
    public void receiveMessage(String command) {
        System.out.println("Received <" + command + ">");

        try {
            Command cmd = mapper.readValue(command, Command.class);
            Thread.sleep(cmd.getCapacity() * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
