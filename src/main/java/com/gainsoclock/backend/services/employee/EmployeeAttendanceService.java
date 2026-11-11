package com.gainsoclock.backend.services.employee;


import com.gainsoclock.backend.dtos.EmployeeAttendanceDto;
import com.gainsoclock.backend.dtos.EmployeeAttendanceListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.types.EmployeeAttendanceActionType;

import java.security.Principal;

public interface EmployeeAttendanceService {
    ResponseDto employeeAttendance(Principal principal, EmployeeAttendanceActionType attendanceAction, EmployeeAttendanceDto dto);

    ResponseDto listEmployeeAttendance(Principal principal, EmployeeAttendanceListDto dto);
}
