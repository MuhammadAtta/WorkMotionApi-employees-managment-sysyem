package com.rest.api.statemachine;

import com.rest.api.model.EmployeeEvents;
import com.rest.api.model.EmployeeStates;
import com.rest.api.statemachine.actions.IdleToActiveAction;
import com.rest.api.statemachine.guards.IdleToActiveGuard;
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
    private IdleToActiveGuard idleToActiveGuard;

    @Autowired
    private IdleToActiveAction idleToActiveAction;

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        Set<String> stringStates = new HashSet<>();
        EnumSet.allOf(EmployeeStates.class).forEach(entity -> stringStates.add(entity.name()));
        states.withStates()
                .initial(EmployeeStates.IDLE.name())
                .end(EmployeeStates.DELETED.name())
                .states(stringStates);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions.withExternal()
                .source(EmployeeStates.IDLE.name()).target(EmployeeStates.ACTIVE.name())
                .event(EmployeeEvents.ACTIVE.name())
                .guard(idleToActiveGuard).action(idleToActiveAction)
                .and().withExternal()
                .source(EmployeeStates.ACTIVE.name()).target(EmployeeStates.IDLE.name())
                .event(EmployeeEvents.IDLE.name())
                .and().withExternal()
                .source(EmployeeStates.IDLE.name()).target(EmployeeStates.DELETED.name())
                .event(EmployeeEvents.DELETED.name())
                .and().withExternal()
                .source(EmployeeStates.ACTIVE.name()).target(EmployeeStates.DELETED.name())
                .event(EmployeeEvents.DELETED.name());
    }
}
