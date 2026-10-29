package com.gainsoclock.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDto {
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String nickname;
}
