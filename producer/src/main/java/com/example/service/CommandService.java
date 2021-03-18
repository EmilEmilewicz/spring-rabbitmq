package com.example.service;

import com.example.entity.Command;
import com.example.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    public Command save(Command command){

        return commandRepository.save(command);
    }
}
