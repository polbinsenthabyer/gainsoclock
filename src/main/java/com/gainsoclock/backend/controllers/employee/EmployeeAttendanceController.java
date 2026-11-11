package com.gainsoclock.backend.controllers.employee;

import com.gainsoclock.backend.dtos.EmployeeAttendanceDto;
import com.gainsoclock.backend.dtos.EmployeeAttendanceListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.employee.EmployeeAttendanceService;
import com.gainsoclock.backend.types.EmployeeAttendanceActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/employee/attendance")
@RequiredArgsConstructor
public class EmployeeAttendanceController {

    private final EmployeeAttendanceService employeeAttendanceService;

    @PostMapping("/{attendanceAction}")
    public ResponseDto employeeAttendance(Principal principal, @PathVariable EmployeeAttendanceActionType action, @RequestBody EmployeeAttendanceDto dto){
        return employeeAttendanceService.employeeAttendance(principal, action, dto);
    }

    @GetMapping("/list")
    public ResponseDto listEmployeeAttendance(Principal principal, @RequestBody EmployeeAttendanceListDto dto){
        return employeeAttendanceService.listEmployeeAttendance(principal, dto);
    }

}
