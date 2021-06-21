package com.rest.api.controller;

import com.rest.api.model.Employee;
import com.rest.api.model.EmployeeEvents;
import com.rest.api.model.EmployeeStates;
import com.rest.api.repository.EmployeeRepository;
import com.rest.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Muhammad Atta
 *
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Autowired
    private EmployeeService employeeService;
    EmployeeRepository employeeRepository;





    // Return Single employee
    @GetMapping("/employees/{id}")
    Optional<Employee> singleEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    // Return All employee
    @RequestMapping(value = "/all")
    public List<Employee> getEmployees() {
        return (List<Employee>) employeeService.findAllEmployees();

    }

    // Create New employee
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Employee createEmployee(@RequestBody Employee employee) {

        return employeeService.createEmployee(employee);
    }







    // Update State employee
    @RequestMapping(value = "/{id}/update/{event}")
    public Boolean sendEvent(@PathVariable("id") Long id, @PathVariable("event") EmployeeEvents events) {
        System.out.println("sendEvent() ====================EmployeeController " );
        updateEventEmployee(new Employee() ,events ,id);
        System.out.println("id ====================EmployeeController "+ id );
        System.out.println("event ====================EmployeeController "+ events );

        System.out.println("new Employee() ====================EmployeeController "+ new Employee().getState(events).name() );

        System.out.println("sendEvent() ==================== updateState() " );

        return employeeService.replaceEmployee(id,events);


    }
    // Update Event employee
    Employee updateEventEmployee(Employee newEmployee, EmployeeEvents events, Long id) {
        System.out.println("updateEventEmployee() ====================EmployeeController " );

        return employeeRepository.findById(id)
                .map(employee -> {
                    //  employee.setState(newEmployee.getState());
                    employee.setState(newEmployee.getState(events));

                    //  employee.setState(events);

                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                });
    }




}
