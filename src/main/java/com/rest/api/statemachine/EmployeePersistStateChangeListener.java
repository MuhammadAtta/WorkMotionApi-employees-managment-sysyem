package com.rest.api.statemachine;

import com.rest.api.model.Employee;
import com.rest.api.model.EmployeeEvents;
import com.rest.api.model.EmployeeStates;
import com.rest.api.repository.EmployeeRepository;
import com.rest.api.service.EmployeeService;
import com.rest.api.utils.EmployeeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler.PersistStateChangeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

/**
 *
 * @author Muhammad Atta
 *
 */

@Component
public class EmployeePersistStateChangeListener implements PersistStateChangeListener {

    private final static Logger logger = LoggerFactory.getLogger(EmployeePersistStateChangeListener.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    EmployeeService employeeService ;



    @Override
    public void onPersist(State<String, String> state,
                          Message<String> message,
                          Transition<String, String> transition,
                          StateMachine<String, String> stateMachine)
    {

        if (message == null && message.getHeaders().containsKey(EmployeeConstants.employeeHeader)) {

            Employee employee = message.getHeaders().get(EmployeeConstants.employeeHeader, Employee.class);
            EmployeeStates events = EmployeeStates.valueOf(employee.getState());

            employeeService.replaceEmployee(employee.getId(),  events);

            employee.setState(employee.getState());
            logger.debug("Persisting: the new employee.. {}", employee);
            employeeRepository.save(employee);

        }
    }

}
