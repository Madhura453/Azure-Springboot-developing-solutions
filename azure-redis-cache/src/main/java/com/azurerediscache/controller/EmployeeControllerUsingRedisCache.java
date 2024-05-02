package com.azurerediscache.controller;

import com.azurerediscache.model.Employee;
import com.azurerediscache.service.EmployeeServiceWithCache;
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
public class EmployeeControllerUsingRedisCache {

    @Autowired
    private EmployeeServiceWithCache employeeServiceWithCache;

    @GetMapping(value = "employees",produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Employee> getAllEmployees() {

       return employeeServiceWithCache.getAllEmployees();
    }

    @GetMapping(value = "employee",produces = MediaType.APPLICATION_JSON_VALUE)
    private Employee getByEmployeeId(@RequestParam("eid") String eid) {

        return employeeServiceWithCache.getByEmployeeId(eid);
    }


    @PostMapping(value = "employee",consumes =MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Employee employee1 = employeeServiceWithCache.saveEmployee(employee);
        return new ResponseEntity<>(employee1, HttpStatus.OK);
    }


}
