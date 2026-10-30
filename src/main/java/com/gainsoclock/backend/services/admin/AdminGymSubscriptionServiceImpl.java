package com.gainsoclock.backend.services.admin;

import com.gainsoclock.backend.dtos.GymSubscription;
import com.gainsoclock.backend.dtos.GymSubscriptionDto;
import com.gainsoclock.backend.dtos.GymSubscriptionListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.repositories.GymSubscriptionRepository;
import com.gainsoclock.backend.utilities.MessageType;
import com.gainsoclock.backend.utilities.ResponseUtility;
import com.gainsoclock.backend.utilities.UserUtility;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGymSubscriptionServiceImpl implements AdminGymSubscriptionService {

    private final GymSubscriptionRepository gymSubscriptionRepository;
    private final UserUtility userUtility;

    @Override
    public ResponseDto setGymSubscription(Principal principal, GymSubscriptionDto dto) {
        if (dto == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }
        User user = userUtility.findUserByPrincipal(principal);

        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasAnyAdministrativeRole(user)){
            return ResponseUtility.getErrorResponse("E0003", MessageType.ERROR_NO_PERMISSION);
        }

        GymSubscription subscription = gymSubscriptionRepository.findByGymSubscriptionType(dto.getGymSubscriptionType()).orElse(null);

        try {
            if (subscription == null){
                subscription = new GymSubscription();
                subscription.setGymSubscriptionType(dto.getGymSubscriptionType());
                subscription.setCreatedBy(user.getUsername());
                subscription.setCreatedAt(new Date());
            }
            subscription.setGymSubscriptionPrice(dto.getGymSubscriptionPrice());
            subscription.setIsGymSubscriptionActive(dto.getIsGymSubscriptionActive() != null ? dto.getIsGymSubscriptionActive(): Boolean.TRUE);
            subscription.setUpdatedBy(user.getUsername());
            subscription.setUpdatedAt(new Date());
            gymSubscriptionRepository.save(subscription);
        } catch (Exception e) {
            return ResponseUtility.getErrorResponse("E9999", MessageType.ERROR_SOMETHING_WENT_WRONG);
        }
        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_SET_GYM_SUBSCRIPTION);
    }

    @Override
    public ResponseDto listGymSubscription(Principal principal, GymSubscriptionListDto dto) {
        if (dto == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }

        User user = userUtility.findUserByPrincipal(principal);
        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasAnyAdministrativeRole(user)){
            return ResponseUtility.getErrorResponse("E0003", MessageType.ERROR_NO_PERMISSION);
        }

        List<GymSubscription> gymSubscriptionList = gymSubscriptionRepository.findAll();
        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_FETCHED_DATA, gymSubscriptionList);
    }

    @Override
    public ResponseDto deleteGymSubscriptionById(Principal principal, Long subscriptionId) {
        User user = userUtility.findUserByPrincipal(principal);
        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasAnyAdministrativeRole(user)){
            return ResponseUtility.getErrorResponse("E0003", MessageType.ERROR_NO_PERMISSION);
        }

        if (subscriptionId == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }

        GymSubscription subscription = gymSubscriptionRepository.findById(subscriptionId).orElse(null);

        if (subscription == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_GYM_SUBSCRIPTION_NOT_EXIST);
        }

        gymSubscriptionRepository.deleteById(subscriptionId);

        return ResponseUtility.getSuccessResponse("E0003", MessageType.SUCCESSFULLY_DELETED_GYM_SUBSCRIPTION);
    }
}
