package com.teleport.must.have.skills.one_three.kafka;

import com.teleport.must.have.skills.one_three.util.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class UserEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserEventProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        logger.info("-------------------------------------------------------"+message);
        kafkaTemplate.send(KafkaUtils.TOPIC, message);
    }
}
