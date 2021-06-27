package com.rest.api.statemachine.guards;

import com.rest.api.model.Employee;
import com.rest.api.utils.EmployeeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

/**
 *
 * @author Muhammad Atta
 *
 */

@Component
public class AddedToCheckGuard implements Guard<String, String> {

    private final static Logger logger = LoggerFactory.getLogger(AddedToCheckGuard.class);

    @Override
    public boolean evaluate(StateContext<String, String> context) {
        Employee employee = (Employee) context.getMessageHeader(EmployeeConstants.employeeHeader);
        if (employee == null) {
            logger.debug("Guard: Wrong transition?");
        } else {
            logger.debug("Guard: protecting the transition.. {}", employee);
        }
        return employee != null;
    }
}
