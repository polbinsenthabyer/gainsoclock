package com.gainsoclock.backend.controllers.admin;

import com.gainsoclock.backend.dtos.GymSubscriptionDto;
import com.gainsoclock.backend.dtos.GymSubscriptionListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.admin.AdminGymSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/gym/subscription")
public class AdminGymSubscriptionController {

    private final AdminGymSubscriptionService adminGymSubscriptionService;

    @PostMapping("/set")
    public ResponseDto setGymSubscription(Principal principal, @RequestBody GymSubscriptionDto dto){
        return adminGymSubscriptionService.setGymSubscription(principal, dto);
    }

    @GetMapping("/list")
    public ResponseDto listGymSubscription(Principal principal, @RequestBody GymSubscriptionListDto dto){
        return adminGymSubscriptionService.listGymSubscription(principal, dto);
    }

    @DeleteMapping("/delete/{subscriptionId}")
    public ResponseDto deleteGymSubscriptionById(Principal principal, @PathVariable Long subscriptionId){
        return adminGymSubscriptionService.deleteGymSubscriptionById(principal, subscriptionId);
    }

}
