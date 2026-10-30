package com.gainsoclock.backend.services.admin;

import com.gainsoclock.backend.dtos.EmployeeDto;
import com.gainsoclock.backend.dtos.EmployeeListDto;
import com.gainsoclock.backend.dtos.ResponseDto;

public interface AdminEmployeeService {

    ResponseDto setEmployee(EmployeeDto dto);

    ResponseDto listEmployee(EmployeeListDto dto);

    ResponseDto deleteEmployeeById(Long id);
}
