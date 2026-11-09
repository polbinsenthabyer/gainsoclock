package com.gainsoclock.backend.services.admin;


import com.gainsoclock.backend.dtos.ListGymClientSubscriptionDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.types.RequestStatusType;

import java.security.Principal;

public interface AdminGymClientSubscriptionService {

    ResponseDto listGymClientSubscription(Principal principal, ListGymClientSubscriptionDto dto);

    ResponseDto updateClientSubscriptionRequest(Principal principal, RequestStatusType requestStatusType, Long requestId);
}
