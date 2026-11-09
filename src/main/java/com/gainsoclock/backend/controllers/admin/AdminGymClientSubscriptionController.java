package com.gainsoclock.backend.controllers.admin;

import com.gainsoclock.backend.dtos.ListGymClientSubscriptionDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.admin.AdminGymClientSubscriptionService;
import com.gainsoclock.backend.types.RequestStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/gym/client/subscription")
public class AdminGymClientSubscriptionController {

    private final AdminGymClientSubscriptionService adminGymClientSubscriptionService;

    @GetMapping("/list")
    public ResponseDto listGymClientSubscription(Principal principal, ListGymClientSubscriptionDto dto){
        return adminGymClientSubscriptionService.   listGymClientSubscription(principal, dto);
    }

    @PostMapping("/{requestStatusType}/{requestId}")
    public ResponseDto updateClientSubscriptionRequest(Principal principal,
                                                       @PathVariable RequestStatusType requestStatusType,
                                                       @PathVariable Long requestId){
        return adminGymClientSubscriptionService.updateClientSubscriptionRequest(principal, requestStatusType, requestId);
    }
}
