package com.gainsoclock.backend.services.admin.employee;

import com.gainsoclock.backend.dtos.EmployeeDto;
import com.gainsoclock.backend.dtos.EmployeeListDto;
import com.gainsoclock.backend.dtos.ResponseDto;

public interface EmployeeAdminService {

    ResponseDto setEmployee(EmployeeDto dto);

    ResponseDto listEmployee(EmployeeListDto dto);

    ResponseDto deleteEmployeeById(Long id);
}
