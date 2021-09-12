package net.squareit.spring.kafka.helloworld.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class SendMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessage.class);

    @Autowired
    private SimpMessagingTemplate template;

    public void send(String content) {
        LOGGER.info("Message send to client: {}", content);
        template.convertAndSend("/global-message/tick", content);
    }
}
