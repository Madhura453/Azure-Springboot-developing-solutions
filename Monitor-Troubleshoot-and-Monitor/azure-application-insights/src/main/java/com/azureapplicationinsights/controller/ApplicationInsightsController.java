package com.azureapplicationinsights.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/madhu")
@Slf4j
public class ApplicationInsightsController {
    private Logger logger= LoggerFactory.getLogger(ApplicationInsightsController.class);
    @GetMapping("/logging")
    public String getName(@RequestParam("name") String name)
    {
        logger.trace("madhura name is"+name);
        logger.debug("this is debug log");
        logger.info("this is info log");
        logger.warn("this is warn log");
        logger.error("this is error  log");
        logger.info("we finish the logging");
        log.warn("this is warn message"+name);
        log.info("this is info message"+name);
        return name;
    }
}
