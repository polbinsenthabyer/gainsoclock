package com.gainsoclock.backend.utilities;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserRole;
import com.gainsoclock.backend.repositories.UserRepository;
import com.gainsoclock.backend.repositories.UserRoleRepository;
import com.gainsoclock.backend.types.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtility {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public ResponseDto findUserByUsername(String username){
        User user =  userRepository.findByUsername(username).orElse(null);

        return user != null ? ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_FETCH_USER_DATA, user)
                : ResponseUtility.getSuccessResponse(MessageType.ERROR_USER_NOT_EXIST);
    }

    public ResponseDto findUserById(long userId){
        User user =  userRepository.findById(userId).orElse(null);

        return user != null ? ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_FETCH_USER_DATA, user)
                : ResponseUtility.getSuccessResponse(MessageType.ERROR_USER_NOT_EXIST);
    }

    public Boolean isUserExistByUsername(String username){
        ResponseDto res = findUserByUsername(username);
        return res.getData() != null;
    }

    public Boolean isUserExistById(long userId){
        ResponseDto res = findUserById(userId);
        return res != null;
    }

    public Long findUserRoleIdByUserRoleType(UserRoleType userRoleType){
        UserRole role = userRoleRepository.findUserRoleByRoleName(userRoleType.name());
        return role.getId();
    }
}
