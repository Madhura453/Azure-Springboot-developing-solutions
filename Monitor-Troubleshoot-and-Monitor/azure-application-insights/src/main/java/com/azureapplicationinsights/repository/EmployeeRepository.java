package com.azureapplicationinsights.repository;

import com.azureapplicationinsights.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
