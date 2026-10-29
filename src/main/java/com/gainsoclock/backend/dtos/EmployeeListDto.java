package com.gainsoclock.backend.dtos;

import lombok.Data;

@Data
public class EmployeeListDto {
    private String username;
    private Boolean isExactMatch;
    private String sortBy;
    private String sortDirection;
    private long size = 10;
    private long page = 0;
}
