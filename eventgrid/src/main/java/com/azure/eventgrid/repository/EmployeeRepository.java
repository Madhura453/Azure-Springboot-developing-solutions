package com.azure.eventgrid.repository;

import com.azure.eventgrid.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
