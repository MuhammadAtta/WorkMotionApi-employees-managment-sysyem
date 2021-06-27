package com.rest.api.config;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Muhammad Atta
 *
 */

@Component
class ContainerFactoryConfigurer {

    ContainerFactoryConfigurer(ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
        factory.getContainerProperties().setMissingTopicsFatal(false);
    }

}
