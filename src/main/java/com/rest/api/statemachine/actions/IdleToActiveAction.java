package com.rest.api.statemachine.actions;

import com.rest.api.model.Employee;
import com.rest.api.utils.EmployeeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 *
 * @author Muhammad Atta
 *
 */

@Component
public class IdleToActiveAction implements Action<String, String> {

    private final static Logger logger = LoggerFactory.getLogger(IdleToActiveAction.class);

    @Override
    public void execute(StateContext<String, String> context) {
        Employee employee = (Employee) context.getMessageHeader(EmployeeConstants.employeeHeader);
        if (employee == null) {
            logger.debug("Action: Wrong transition?");
        } else {
            logger.debug("Action: changing the idle employee to active.. {}", employee);
        }
    }
}
