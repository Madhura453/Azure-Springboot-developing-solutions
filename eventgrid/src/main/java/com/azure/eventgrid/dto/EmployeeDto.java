package com.azure.eventgrid.dto;

import com.azure.eventgrid.annotation.Fixed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @Fixed(startIndex = 0, endIndex = 2)
    public String eid;
    @Fixed(startIndex = 2, endIndex = 5)
    public String name;
    @Fixed(startIndex = 5, endIndex = 6)
    public String dept;
}
