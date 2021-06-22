package com.rest.api.statemachine;

import com.rest.api.model.EmployeeEvents;
import com.rest.api.model.EmployeeStates;
import com.rest.api.statemachine.actions.AddedToActiveAction;
import com.rest.api.statemachine.guards.AddedToActiveGuard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Muhammad Atta
 *
 */

@Configuration
@EnableStateMachine(name = "entityStateMachine")
public class EmployeeStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    @Autowired
    private AddedToActiveGuard addedToActiveGuard;


    @Autowired
    private AddedToActiveAction addedToActiveAction;

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        Set<String> stringStates = new HashSet<>();
        EnumSet.allOf(EmployeeStates.class).forEach(entity -> stringStates.add(entity.name()));
        states.withStates()
                .initial(EmployeeStates.ADDED.name())
                .end(EmployeeStates.APPROVED.name())
                .states(stringStates);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions.withExternal()
                .source(EmployeeStates.ADDED.name()).target(EmployeeStates.ACTIVE.name())
                .event(EmployeeEvents.ACTIVE.name())
                .guard(addedToActiveGuard).action(addedToActiveAction)
                .and().withExternal()
                .source(EmployeeStates.ACTIVE.name()).target(EmployeeStates.ADDED.name())
                .event(EmployeeEvents.ADDED.name())
                .and().withExternal()
                .source(EmployeeStates.ADDED.name()).target(EmployeeStates.IN_CHECK.name())
                .event(EmployeeEvents.IN_CHECK.name())
                .and().withExternal()
                .source(EmployeeStates.ACTIVE.name()).target(EmployeeStates.APPROVED.name())
                .event(EmployeeEvents.APPROVED.name());
    }
}
