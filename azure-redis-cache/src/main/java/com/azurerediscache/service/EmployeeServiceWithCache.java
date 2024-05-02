package com.azurerediscache.service;

import com.azurerediscache.model.Employee;
import com.azurerediscache.model.Order;
import com.azurerediscache.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceWithCache {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private ObjectMapper objectMapper=new ObjectMapper();

    public List<Employee> getAllEmployees()
    {
        ValueOperations valueOperations=this.redisTemplate.opsForValue();

        if(!redisTemplate.hasKey("employees"))
        {
            List<Employee> orderList=employeeRepository.findAll();
            valueOperations.set("employees",orderList);
            log.info("list of employees is getting from db");
            return orderList;
        }
        log.info("list of employees is getting from reddis catche");
        return (List<Employee>) valueOperations.get("employees");
    }

    public Employee getByEmployeeId(String eid)
    {
        return employeeRepository.findById(eid).get();
    }

    public Employee saveEmployee(Employee employee)
    {
        ValueOperations valueOperations=this.redisTemplate.opsForValue();
        Employee employee1=employeeRepository.save(employee);
        List<Employee> orderList=employeeRepository.findAll();
        log.info("list of employees is setting to cache");
        valueOperations.set("employees",orderList);
        return employee1;
    }
}
