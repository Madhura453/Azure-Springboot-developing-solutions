package com.azure.eventgrid.dto.mapper;

import com.azure.eventgrid.dto.EmployeeDto;
import com.azure.eventgrid.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee dtoToDtoEntity(EmployeeDto employeeDto);

}
