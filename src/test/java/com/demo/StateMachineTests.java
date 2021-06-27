package com.demo;

import com.rest.api.statemachine.EmployeeStateMachineConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Muhammad Atta
 *
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { EmployeeStateMachineConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StateMachineTests {

    @Autowired
    private StateMachineFactory<String, String> stateMachineFactory;

    private StateMachine<String, String> stateMachine;



    @BeforeEach
    public void setup() throws Exception {
        stateMachine = stateMachineFactory.getStateMachine();
        // plan don't know how to wait if machine is started
        // automatically so wait here.
        for (int i = 0; i < 10; i++) {
            if (stateMachine.getState() != null) {
                break;
            } else {
                Thread.sleep(200);
            }
        }
    }

    @Test
    public void testInitial() throws Exception {
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState("ADDED")
                        .and()
                        .build();
        plan.test();
    }


    @Test
    public void testEmployee() throws Exception {
        // TODO: REACTOR check if less changes is good
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .stateMachine(stateMachine)
                        .step()
                      //  .expectState("WAIT_NEW_ORDER")
                        .expectState("IN_CHECK")

                        .and()
                        .step()
                        .sendEvent(MessageBuilder.withPayload("CHECKING")
                                .setHeader("employee", "employee1").build())
                        .expectStates("IN_CHECK", "APPROVED", "ACTIVE")

                        .expectStateChanged(3)
                        .and()
                        .step()
                       // .sendEvent(MessageBuilder.withPayload("RECEIVE_PAYMENT")
                        .sendEvent(MessageBuilder.withPayload("APPROVE")
                               .setHeader("employee", "1000").build())
                        .expectStates("ACTIVE")
                        .expectStateChanged(2)
                        .expectStateMachineStopped(3)
                        .and()
                        .build();
        plan.test();
    }


}
