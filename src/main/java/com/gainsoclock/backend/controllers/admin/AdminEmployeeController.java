package com.gainsoclock.backend.controllers.admin;

import com.gainsoclock.backend.dtos.EmployeeDto;
import com.gainsoclock.backend.dtos.EmployeeListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.admin.employee.AdminEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/employee")
@RequiredArgsConstructor
public class AdminEmployeeController {

    private final AdminEmployeeService adminEmployeeService;

    @PostMapping("/set")
    public ResponseDto setEmployee(@RequestBody EmployeeDto dto){
        return adminEmployeeService.setEmployee(dto);
    }

    @GetMapping("/list")
    public ResponseDto listEmployee(@RequestBody EmployeeListDto dto){
        return adminEmployeeService.listEmployee(dto);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseDto deleteEmployeeById(@PathVariable long employeeId){
        return adminEmployeeService.deleteEmployeeById(employeeId);
    }
}
