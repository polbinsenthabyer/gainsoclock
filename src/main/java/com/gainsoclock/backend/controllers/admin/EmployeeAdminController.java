package com.gainsoclock.backend.controllers.admin;

import com.gainsoclock.backend.dtos.EmployeeDto;
import com.gainsoclock.backend.dtos.EmployeeListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.admin.employee.EmployeeAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/employee")
@RequiredArgsConstructor
public class EmployeeAdminController {

    private final EmployeeAdminService employeeAdminService;

    @PostMapping("/set")
    public ResponseDto setEmployee(@RequestBody EmployeeDto dto){
        return employeeAdminService.setEmployee(dto);
    }

    @GetMapping("/list")
    public ResponseDto listEmployee(@RequestBody EmployeeListDto dto){
        return employeeAdminService.listEmployee(dto);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseDto deleteEmployeeById(@PathVariable long employeeId){
        return employeeAdminService.deleteEmployeeById(employeeId);
    }
}
