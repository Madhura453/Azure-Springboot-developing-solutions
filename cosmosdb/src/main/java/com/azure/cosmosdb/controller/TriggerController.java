package com.azure.cosmosdb.controller;

import com.azure.cosmosdb.Model.Course;
import com.azure.cosmosdb.service.NoSQlTriggersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriggerController {

    @Autowired
    private NoSQlTriggersService triggersService;

    @PostMapping("/trigger/course")
    public String saveCourse(@RequestBody Course course)
    {
        triggersService.timeStampParamereCheckingTriggerExecution(course);
        return "timestamp trigger executed successfully";
    }
}
