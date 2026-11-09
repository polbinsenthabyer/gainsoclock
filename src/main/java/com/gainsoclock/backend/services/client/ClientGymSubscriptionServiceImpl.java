package com.gainsoclock.backend.services.client;

import com.gainsoclock.backend.dtos.ClientGymSubscriptionDto;
import com.gainsoclock.backend.dtos.ClientGymSubscriptionListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.GymSubscription;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserGymSubscription;
import com.gainsoclock.backend.repositories.GymSubscriptionRepository;
import com.gainsoclock.backend.repositories.UserGymSubscriptionRepository;
import com.gainsoclock.backend.types.GymSubscriptionStatusType;
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
public class ClientGymSubscriptionServiceImpl implements ClientGymSubscriptionService{

    private final UserGymSubscriptionRepository userGymSubscriptionRepository;
    private final GymSubscriptionRepository gymSubscriptionRepository;
    private final UserUtility userUtility;

    @Override
    public ResponseDto requestGymSubscription(Principal principal, ClientGymSubscriptionDto dto) {

        if (dto == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }

        User user = userUtility.findUserByPrincipal(principal);
        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasClientRole(user)){
            return ResponseUtility.getErrorResponse("E0003",MessageType.ERROR_NO_PERMISSION);
        }

        UserGymSubscription hasActiveGymSubscription = userGymSubscriptionRepository.findByUserIdAndGymSubscriptionStatus(user.getId(), GymSubscriptionStatusType.ACTIVE.name()).orElse(null);
        if (hasActiveGymSubscription != null){
            return ResponseUtility.getErrorResponse("E0004", MessageType.ERROR_USER_HAS_ACTIVE_SUBSCRIPTION);
        }

        UserGymSubscription hasPendingRequestGymSubscription = userGymSubscriptionRepository.findByUserIdAndGymSubscriptionStatus(user.getId(), GymSubscriptionStatusType.REQUESTED.name()).orElse(null);
        if (hasPendingRequestGymSubscription != null){
            return ResponseUtility.getErrorResponse("E0004", MessageType.ERROR_USER_HAS_PENDING_SUBSCRIPTION_REQUEST);
        }

        if (dto.getGymSubscriptionRequestStartDate().equals(dto.getGymSubscriptionRequestEndDate())){
            return ResponseUtility.getErrorResponse("E0005", MessageType.ERROR_INVALID_GYM_SUBSCRIPTION_REQUEST_DATE);
        }

        if (dto.getGymSubscriptionRequestEndDate().before(dto.getGymSubscriptionRequestStartDate())){
            return ResponseUtility.getErrorResponse("E0006", MessageType.ERROR_INVALID_GYM_SUBSCRIPTION_REQUEST_DATE);
        }

        if (dto.getGymSubscriptionRequestStartDate().before(new Date())){
            return ResponseUtility.getErrorResponse("E0007", MessageType.ERROR_INVALID_GYM_SUBSCRIPTION_REQUEST_DATE);
        }

        GymSubscription subscription = gymSubscriptionRepository.findByGymSubscriptionType(dto.getGymSubscriptionType()).orElse(null);
        if (subscription == null){
            return ResponseUtility.getErrorResponse("E0008", MessageType.ERROR_GYM_SUBSCRIPTION_NOT_EXIST);
        }

        if (subscription.getIsGymSubscriptionActive().equals(Boolean.FALSE)){
            return ResponseUtility.getErrorResponse("E0009", MessageType.ERROR_SELECTED_GYM_SUBSCRIPTION_TYPE_IS_INACTIVE);
        }

        userGymSubscriptionRepository.save(buildGymSubscriptionRequest(user, subscription, dto));

        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_PROCESSED_DATA);
    }

    private UserGymSubscription buildGymSubscriptionRequest(User user, GymSubscription subscription, ClientGymSubscriptionDto dto) {
        return UserGymSubscription.builder()
                .userId(user.getId())
                .gymSubscriptionType(subscription.getGymSubscriptionType())
                .gymSubscriptionStatus(GymSubscriptionStatusType.REQUESTED)
                .gymSubscriptionStartDate(dto.getGymSubscriptionRequestStartDate())
                .gymSubscriptionEndDate(dto.getGymSubscriptionRequestEndDate())
                .createdAt(new Date())
                .createdBy(user.getUsername())
                .updatedAt(new Date())
                .updatedBy(user.getUsername())
                .build();
    }

    @Override
    public ResponseDto listGymSubscription(Principal principal, ClientGymSubscriptionListDto dto) {

        if (dto == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }

        User user = userUtility.findUserByPrincipal(principal);
        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasClientRole(user)){
            return ResponseUtility.getErrorResponse("E0003",MessageType.ERROR_NO_PERMISSION);
        }

        List<UserGymSubscription> list = userGymSubscriptionRepository.findAllByUserId(user.getId());
        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_FETCHED_DATA, list);
    }

    @Override
    public ResponseDto detailGymSubscription(Principal principal){
        return null;
    }

}
