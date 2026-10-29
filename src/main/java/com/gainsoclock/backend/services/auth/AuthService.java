package com.gainsoclock.backend.services.auth;


import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.dtos.SignupDto;
import com.gainsoclock.backend.dtos.LoginDto;

public interface AuthService {

    ResponseDto signup (SignupDto dto);

    ResponseDto login (LoginDto dto);
}
