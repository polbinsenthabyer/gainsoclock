package com.gainsoclock.backend.services.admin;

import com.gainsoclock.backend.dtos.GymSubscriptionDto;
import com.gainsoclock.backend.dtos.GymSubscriptionListDto;
import com.gainsoclock.backend.dtos.ResponseDto;

import java.security.Principal;

public interface AdminGymSubscriptionService {

    ResponseDto setGymSubscription(Principal principal, GymSubscriptionDto dto);

    ResponseDto listGymSubscription(Principal principal, GymSubscriptionListDto dto);

    ResponseDto deleteGymSubscriptionById(Principal principal, Long subscriptionId);
}
