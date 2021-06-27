package com.rest.api.controller;

import com.rest.api.model.Employee;
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
    @GetMapping("/employee/{id}")
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
    public Boolean sendEvent(@PathVariable("id") Long id, @PathVariable("event") EmployeeStates events) {
        updateEventEmployee(new Employee() ,events ,id);

         employeeService.replaceEmployee(id,events);
        return true;
    }
    // Update Event employee
    Employee updateEventEmployee(Employee newEmployee, EmployeeStates events, Long id) {

        return employeeRepository.findById(id)
                .map(employee -> {
                   employee.setState(newEmployee.getState(String.valueOf(events)));
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                });
    }
}
