package com.docker.dockercomposemysqlapplication;

import com.docker.dockercomposemysqlapplication.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DockerComposeMysqlApplication implements CommandLineRunner {

	@Autowired
	public EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(DockerComposeMysqlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(employeeRepository.findAll());
		log.info("employee list is:{}",employeeRepository.findAll());
		log.info("employee list number:{}",employeeRepository.findAll().size());
	}
}
