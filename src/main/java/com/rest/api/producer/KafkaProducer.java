package com.rest.api.producer;

import com.rest.api.constant.ApplicationConstant;
import com.rest.api.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Muhammad Atta
 *
 */

@RestController
@RequestMapping("/produce")
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/message")
    public String sendMessage(@RequestBody Employee message) {

        try {
            kafkaTemplate.send(ApplicationConstant.TOPIC_NAME, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "json message sent successfully";
    }

}
