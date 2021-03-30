package com.example.config;

import com.example.listener.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    TopicExchange exchange(@Value("${rabbit.topic.message}") String topicName) {
        return new TopicExchange(topicName);
    }

    @Bean
    Queue queue(@Value("${rabbit.queue.message}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Binding binding(@Value("${rabbit.queue.message}") String queueName,
                    Queue queue, TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    SimpleMessageListenerContainer container(@Value("${rabbit.queue.message}") String queueName,
                                             ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {

        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receive");
    }
}
