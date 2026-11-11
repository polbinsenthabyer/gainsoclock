package com.gainsoclock.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeAttendanceDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date clockInTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date clockOutTime;


}
