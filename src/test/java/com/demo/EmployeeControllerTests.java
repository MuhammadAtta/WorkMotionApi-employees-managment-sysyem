package com.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.ApplicationApi;
import com.rest.api.model.Employee;
import com.rest.api.model.EmployeeStates;
import com.rest.api.service.EmployeeService;
import com.rest.api.controller.EmployeeController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Muhammad Atta
 *
 */

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@SpringBootTest(classes = ApplicationApi.class)
@AutoConfigureMockMvc
@ImportResource({"classpath*:applicationContext.xml"})
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTests {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private EmployeeService service;
    private List<Employee> employeeList;
    private Employee employee;
    private String serviceUri;

    @Autowired
    //private ObjectMapper mapper  ;
    private String jsonEmployeeList;
    private String jsonEmployee;


    @Before
    public void setup() throws JsonProcessingException {
        employee = new Employee(10L, "John", "ADDED");


        employeeList = Arrays.asList( new Employee(1L,"Juan", "ADDED"),
                new Employee(2L, "Adams", "ADDED"));


        serviceUri = "/employee";
        ObjectMapper mapper = new ObjectMapper();
        jsonEmployeeList = mapper.writeValueAsString(employee);
        jsonEmployee = mapper.writeValueAsString(employee);
    }

    @Test
    public void getAllEmployees() throws Exception {
        Mockito.when(service.findAllEmployees()).thenReturn(employeeList);
        mockMvc.perform(get(serviceUri))
                .andExpect(content().json(jsonEmployeeList))
                .andExpect(status().isOk());
        Mockito.verify(service).findAllEmployees();
    }


    @Test
    public void getEmployeeById() throws Exception {
        Mockito.when(service.getEmployee(10L)).thenReturn(java.util.Optional.ofNullable(employee));
         mockMvc.perform(get(serviceUri + "{idEmployee}", 10L))
                .andExpect(content().json(jsonEmployee))
                .andExpect(status().isOk());
        Mockito.verify(service).getEmployee(10L);
    }

    @Test
    public void createEmployee() throws Exception {
        Employee toCreate = new Employee(11L, "SAMI", "ADDED");
        Mockito.when(service.createEmployee(toCreate)).thenReturn(toCreate);
        ObjectMapper mapper = new ObjectMapper();
        String toCreatejson = mapper.writeValueAsString(toCreate);

        mockMvc.perform(post(serviceUri)
                .content(toCreatejson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(toCreatejson));

        Mockito.verify(service).createEmployee(toCreate);
    }

    @Test
    public void updateEmployee() throws Exception {


        Employee toUpdate = new Employee(11L, "SAMI", "ADDED");

        EmployeeStates event = EmployeeStates.valueOf(employee.getState());
        Mockito.when(service.replaceEmployee(10L, event)).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String toUpdateString =  mapper.writeValueAsString(toUpdate);
        String expectedToJson = mapper.writeValueAsString(employee);

        mockMvc.perform(patch(serviceUri + "{idEmployee}", 10L)
                .content(toUpdateString)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(expectedToJson))
                .andExpect(status().isOk());

        Mockito.verify(service).replaceEmployee(10L, event);
    }


}