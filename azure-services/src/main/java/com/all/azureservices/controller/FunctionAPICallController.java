package com.all.azureservices.controller;

import com.all.azureservices.dto.Employee;
import com.all.azureservices.service.FunctionAPICallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/function/apicall")
public class FunctionAPICallController {

    @Autowired
    private FunctionAPICallService functionAPICallService;

    @GetMapping(value = "/publish")
    public List<Employee> getAllEmployees(
    ) {
        return functionAPICallService.getAllEmployeesByFunctionUrl();
    }
}
