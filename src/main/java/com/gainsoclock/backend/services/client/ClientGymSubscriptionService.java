package com.gainsoclock.backend.services.client;

import com.gainsoclock.backend.dtos.ClientGymSubscriptionDto;
import com.gainsoclock.backend.dtos.ClientGymSubscriptionListDto;
import com.gainsoclock.backend.dtos.ResponseDto;

import java.security.Principal;

public interface ClientGymSubscriptionService {

    ResponseDto requestGymSubscription(Principal principal, ClientGymSubscriptionDto dto);

    ResponseDto listGymSubscription(Principal principal, ClientGymSubscriptionListDto dto);

    ResponseDto detailGymSubscription(Principal principal);
}
