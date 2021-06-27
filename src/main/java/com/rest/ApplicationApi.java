package com.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;

/**
 *
 * @author Muhammad Atta
 *
 */

@ComponentScan(basePackages = "com.rest.api.*")
@SpringBootApplication
@EntityScan
public class ApplicationApi {
    public static void main(String[] args) {
        System.setProperty("server.servlet.context-path", "/employee");

        try {
            SpringApplication.run(ApplicationApi.class, args);
        }
        catch (ApplicationContextException ace) {
            if (ace.getCause() instanceof IllegalStateException) {
                ace.printStackTrace();
                System.exit(1);
            }
        }
    }


    @KafkaListener(id = "so600369455", topics = "so60036945aa")
    public void listen(String in) {
        System.out.println(in);
    }

}