package com.docker.dockercomposemysqlapplication.repository;


import com.docker.dockercomposemysqlapplication.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
