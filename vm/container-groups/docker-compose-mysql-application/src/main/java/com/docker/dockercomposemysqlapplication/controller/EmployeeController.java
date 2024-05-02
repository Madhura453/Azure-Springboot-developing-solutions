package com.docker.dockercomposemysqlapplication.controller;

import com.docker.dockercomposemysqlapplication.model.Employee;
import com.docker.dockercomposemysqlapplication.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/madhura/alone")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/employees",produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Employee> getAllEmployees1() {
        return employeeRepository.findAll();
    }


    @GetMapping(value = "/employee",produces = MediaType.APPLICATION_JSON_VALUE)
    private Employee getByEmployeeId(@RequestParam("eid") String eid) {
        return employeeRepository.findById(eid).get();
    }

    @PostMapping(value = "/employee",consumes =MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee employee1 = employeeRepository.save(employee);
        return new ResponseEntity<>(employee1, HttpStatus.OK);
    }

    @GetMapping(value = "/text")
    private String getByEmployeeId() {
        String envStr = System.getenv("SQLAZURECONNSTR_connectionstring");
        return "ayyyo madhu"+ envStr;
    }


}
