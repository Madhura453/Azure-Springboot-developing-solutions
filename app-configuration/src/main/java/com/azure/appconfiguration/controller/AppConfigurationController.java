package com.azure.appconfiguration.controller;

//import com.azure.appconfiguration.config.MessageConfig;
import com.azure.appconfiguration.config.MessageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/madhura/alone")
@Slf4j
public class AppConfigurationController {

    @Autowired
    private MessageConfig messageConfig;

    @GetMapping(value = "/app")
    private String getMeesage() {
        log.info("Get user details endpoint invoked.");
        log.info("Meassage from app configuration is: {}",messageConfig.getMessage());
     return messageConfig.getMessage();
    }
}
