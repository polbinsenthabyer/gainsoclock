package com.gainsoclock.backend.controllers;

import com.gainsoclock.backend.dtos.LoginDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.dtos.SignupDto;
import com.gainsoclock.backend.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto dto){
        return authService.signup(dto);
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginDto dto){
        return authService.login(dto);
    }

}
