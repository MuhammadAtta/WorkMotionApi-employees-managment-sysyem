package com.rest.api.consumer;

import com.rest.api.constant.ApplicationConstant;
import com.rest.api.model.Employee;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Muhammad Atta
 *
 */

@RestController
@RequestMapping("/consume")
public class KafkaConsumer {

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, Employee> factory;

    @GetMapping("/message")
    public List<Employee> receiveMessage() {
        List<Employee> employees = new ArrayList<>();
        ConsumerFactory<String, Employee> consumerFactory = (ConsumerFactory<String, Employee>) factory.getConsumerFactory();
        Consumer<String, Employee> consumer = consumerFactory.createConsumer();
        try {
            consumer.subscribe(Arrays.asList(ApplicationConstant.TOPIC_NAME));
            ConsumerRecords<String, Employee> consumerRecords = consumer.poll(10000);
            Iterable<ConsumerRecord<String, Employee>> records = consumerRecords.records(ApplicationConstant.TOPIC_NAME);
            Iterator<ConsumerRecord<String, Employee>> iterator = records.iterator();

            while (iterator.hasNext()) {
                employees.add(iterator.next().value());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
}