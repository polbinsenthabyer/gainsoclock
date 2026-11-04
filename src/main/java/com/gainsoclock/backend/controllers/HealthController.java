package com.gainsoclock.backend.controllers;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.utilities.MessageType;
import com.gainsoclock.backend.utilities.ResponseUtility;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/health")
public class HealthController {

    @GetMapping("/check")
    public ResponseDto healthCheckController(){
        return ResponseUtility.getSuccessResponse(MessageType.API_HEALTH_CHECK);
    }

    @GetMapping("/check/prelive")
    public ResponseDto preliveHealthCheckController(){
        return ResponseUtility.getSuccessResponse(MessageType.API_PRELIVE_HEALTH_CHECK);
    }
}
