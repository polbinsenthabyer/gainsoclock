package com.gainsoclock.backend.utilities;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserGymActivityLog;
import com.gainsoclock.backend.entities.UserGymSubscriptionQr;
import com.gainsoclock.backend.repositories.UserGymActivityLogRepository;
import com.gainsoclock.backend.repositories.UserRepository;
import com.gainsoclock.backend.types.UserActionTypes;
import com.gainsoclock.backend.types.UserGymMemberSubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserActivityUtil {

    private final UserGymActivityLogRepository userGymActivityLogRepository;
    private final UserRepository userRepository;

    public ResponseDto logSubscribedMemberActivity(User user, UserGymSubscriptionQr subscription){

        UserGymActivityLog userLog = userGymActivityLogRepository.findByUserIdAndUserGymActivityDate(user.getId(), LocalDate.now()).orElse(null);

        if (userLog == null) {
            userLog = new UserGymActivityLog();
            userLog.setUserId(user.getId());
            userLog.setUserGymMembershipId(subscription.getUserGymSubscriptionId());
            userLog.setUserGymMembershipSubscriptionType(UserGymMemberSubscriptionType.SUBSCRIBED);
            userLog.setUserActivity(UserActionTypes.CLOCK_IN);
            userLog.setUserGymActivityDate(LocalDate.now());
            userLog.setCreatedAt(new Date());
            userLog.setCreatedBy(user.getUsername());
            userLog.setUpdatedAt(new Date());
            userLog.setUpdatedBy(user.getUsername());

            user.setLastVisitAt(new Date());
            userGymActivityLogRepository.save(userLog);
            userRepository.save(user);

            return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_CLOCKED_IN);

        } else {
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_USER_ALREADY_CLOCKED_IN);
        }
    }

    /**/
    public ResponseDto logNonSubscribedMemberActivity(){
        return null;
    }

}
