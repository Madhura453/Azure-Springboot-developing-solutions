package com.dockerforhelloword.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloWord {

    @GetMapping("/hello")
    public String sayhello()
    {
        return "hello spring boot docker";
    }
}
