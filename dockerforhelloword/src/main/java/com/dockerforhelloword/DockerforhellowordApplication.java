package com.dockerforhelloword;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DockerforhellowordApplication {

	public static void main(String[] args) {
		System.out.println("i am running");
		SpringApplication.run(DockerforhellowordApplication.class, args);
	}

}
