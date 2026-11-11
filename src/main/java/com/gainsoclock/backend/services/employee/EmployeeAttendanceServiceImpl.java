package com.gainsoclock.backend.services.employee;

import com.gainsoclock.backend.dtos.EmployeeAttendanceDto;
import com.gainsoclock.backend.dtos.EmployeeAttendanceListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.types.EmployeeAttendanceActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {

    @Override
    public ResponseDto employeeAttendance(Principal action, EmployeeAttendanceActionType employeeId, EmployeeAttendanceDto dto) {
        return null;
    }

    @Override
    public ResponseDto listEmployeeAttendance(Principal principal, EmployeeAttendanceListDto dto) {
        return null;
    }


}
