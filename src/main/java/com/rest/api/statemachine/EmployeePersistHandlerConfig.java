package com.rest.api.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;

/**
 *
 * @author Muhammad Atta
 *
 */

@Configuration
public class EmployeePersistHandlerConfig {

    @Autowired
    private StateMachine<String, String> entityStateMachine;

    @Autowired
    private EmployeePersistStateChangeListener employeePersistStateChangeListener;

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        PersistStateMachineHandler handler = new PersistStateMachineHandler(entityStateMachine);
        handler.addPersistStateChangeListener(employeePersistStateChangeListener);
        return handler;
    }
}
