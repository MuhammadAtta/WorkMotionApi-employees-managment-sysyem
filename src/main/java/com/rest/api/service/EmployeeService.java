package com.rest.api.service;


import com.rest.api.model.Employee;
import com.rest.api.model.EmployeeStates;
import com.rest.api.repository.EmployeeRepository;
import com.rest.api.utils.EmployeeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.recipes.persist.PersistStateMachineHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author Muhammad Atta
 *
 */

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PersistStateMachineHandler persistStateMachineHandler;

    public Iterable<Employee> findAllEmployees() {
        Iterable<Employee> iterable = employeeRepository.findAll();
        for (Employee employee : iterable) {
            System.out.println(employee);
        }
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Boolean replaceEmployee(Long id, EmployeeStates event) {

        Optional<Employee> employee = employeeRepository.findById(id);
        return persistStateMachineHandler.handleEventWithState(
                MessageBuilder.withPayload(event.name()).setHeader(EmployeeConstants.employeeHeader, employee).build(),
                employee.get().getState()

        );
    }
}
