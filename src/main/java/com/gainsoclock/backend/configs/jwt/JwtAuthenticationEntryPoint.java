package com.gainsoclock.backend.configs.jwt;

import com.gainsoclock.backend.utilities.MessageType;
import com.gainsoclock.backend.utilities.ResponseUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        String jsonErrorMessage = objectMapper.writeValueAsString(ResponseUtility.getErrorResponse("E9999", MessageType.ERROR_NO_PERMISSION));
        writer.write(jsonErrorMessage);
        writer.flush();
    }

}
