package com.azure.eventgrid.service;

import com.azure.eventgrid.dto.EmployeeDto;

public interface EventGridService {
    void readEmployeeFile(String fileName);

    void persistEmployeeObject(String content);

    EmployeeDto generateEmployee(String line);
}
