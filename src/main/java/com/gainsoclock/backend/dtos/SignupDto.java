package com.gainsoclock.backend.dtos;

import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String password;
    private String name;
    private String nickname;
}
