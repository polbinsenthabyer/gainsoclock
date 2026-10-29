package com.gainsoclock.backend.utilities;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final PasswordEncoder passwordEncoder;

    public String hashPassword(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    public boolean isPasswordMatched(String submittedPassword, String hashedPassword){
        return passwordEncoder.matches(submittedPassword, hashedPassword);
    }
}
