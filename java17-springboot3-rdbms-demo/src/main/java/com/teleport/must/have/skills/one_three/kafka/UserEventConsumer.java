package com.teleport.must.have.skills.one_three.kafka;

import com.teleport.must.have.skills.one_three.util.KafkaUtils;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class UserEventConsumer {
    @KafkaListener(topics = KafkaUtils.TOPIC, groupId = "user-group")
    public void consumeMessage(String message) {
        System.out.println("Consumed message: " + message);
    }
}
