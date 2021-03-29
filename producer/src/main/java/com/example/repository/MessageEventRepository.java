package com.example.repository;

import com.example.entity.MessageEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageEventRepository extends JpaRepository<MessageEvent, Long> {
}
