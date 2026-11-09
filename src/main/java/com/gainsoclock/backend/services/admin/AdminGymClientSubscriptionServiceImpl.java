package com.gainsoclock.backend.services.admin;

import com.gainsoclock.backend.dtos.ListGymClientSubscriptionDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserGymSubscription;
import com.gainsoclock.backend.repositories.UserGymSubscriptionRepository;
import com.gainsoclock.backend.types.GymSubscriptionStatusType;
import com.gainsoclock.backend.types.RequestStatusType;
import com.gainsoclock.backend.utilities.MessageType;
import com.gainsoclock.backend.utilities.ResponseUtility;
import com.gainsoclock.backend.utilities.UserUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGymClientSubscriptionServiceImpl implements AdminGymClientSubscriptionService{

    private final UserGymSubscriptionRepository userGymSubscriptionRepository;
    private final UserUtility userUtility;

    @Override
    public ResponseDto listGymClientSubscription(Principal principal, ListGymClientSubscriptionDto dto) {
        List<UserGymSubscription> userGymSubscriptions = userGymSubscriptionRepository.findAll();
        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_FETCH_USER_DATA, userGymSubscriptions);
    }

    @Override
    public ResponseDto updateClientSubscriptionRequest(Principal principal, RequestStatusType requestStatusType, Long requestId) {

        if (requestStatusType == null || requestId == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_INVALID_REQUEST);
        }

        User user = userUtility.findUserByPrincipal(principal);
        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasAnyAdministrativeRole(user)){
            return ResponseUtility.getErrorResponse("E0003",MessageType.ERROR_NO_PERMISSION);
        }

        UserGymSubscription request = userGymSubscriptionRepository.findById(requestId).orElse(null);
        if (request == null){
            return ResponseUtility.getErrorResponse("E0004",MessageType.ERROR_USER_GYM_SUBSCRIPTION_REQUEST_NOT_EXIST);
        }

        return switch (requestStatusType){
            case APPROVED -> processApprovalOfClientGymSubscriptionRequest(user, request);
            case REJECTED -> processRejectionOfClientGymSubscriptionRequest(user, request);
            default -> ResponseUtility.getErrorResponse("E9999", MessageType.ERROR_INVALID_REQUEST);
        };
    }

    private ResponseDto processApprovalOfClientGymSubscriptionRequest(User user, UserGymSubscription request) {
        request.setUpdatedBy(user.getUsername());
        request.setUpdatedAt(new Date());
        request.setGymSubscriptionStatus(GymSubscriptionStatusType.ACTIVE);
        userGymSubscriptionRepository.save(request);

        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_PROCESSED_CLIENT_GYM_SUBSCRIPTION_REQUEST);
    }

    private ResponseDto processRejectionOfClientGymSubscriptionRequest(User user, UserGymSubscription request) {
        request.setUpdatedBy(user.getUsername());
        request.setUpdatedAt(new Date());
        request.setGymSubscriptionStatus(GymSubscriptionStatusType.REJECTED);
        userGymSubscriptionRepository.save(request);

        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_PROCESSED_CLIENT_GYM_SUBSCRIPTION_REQUEST);
    }
}
