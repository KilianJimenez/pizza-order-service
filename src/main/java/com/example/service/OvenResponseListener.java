package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

@Service
public class OvenResponseListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(OvenResponseListener.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String receivedMessage;

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    @Override
    @RabbitListener(queues = "order-completed-queue")
    public void onMessage(Message message) {
        log.info("Received order response from oven: {}", message.toString());
        receivedMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        latch.countDown();
    }
}
