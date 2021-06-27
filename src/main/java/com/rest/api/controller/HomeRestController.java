package com.rest.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Muhammad Atta
 *
 */

@RestController
@RequestMapping(value = "/")
public class HomeRestController {
    @GetMapping
    public String sayHello() {
        return "Welcome to the Employees Management System ApplicationApi. API endpoint.";
    }
}
