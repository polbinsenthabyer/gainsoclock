package com.gainsoclock.backend.utilities;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserRole;
import com.gainsoclock.backend.repositories.UserRepository;
import com.gainsoclock.backend.repositories.UserRoleRepository;
import com.gainsoclock.backend.types.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserUtility {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public ResponseDto findUserById(long userId){
        User user =  userRepository.findById(userId).orElse(null);

        return user != null ? ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_FETCH_USER_DATA, user)
                : ResponseUtility.getSuccessResponse(MessageType.ERROR_USER_NOT_EXIST);
    }

    public Boolean isUserExistByUsername(String username){
        User res = findByUsername(username);
        return res != null;
    }

    public Boolean isUserExistById(long userId){
        ResponseDto res = findUserById(userId);
        return res != null;
    }

    public Long findUserRoleIdByUserRoleType(UserRoleType userRoleType){
        UserRole role = userRoleRepository.findUserRoleByRoleName(userRoleType.name());
        return role.getId();
    }

    public UserRole findUserRoleByUserRoleId(Long roleId){
        return userRoleRepository.findById(roleId).orElse(null);
    }

    public User findUserByPrincipal(Principal principal) {
        return findByUsername(principal.getName());
    }

    public boolean hasAnyAdministrativeRole(User user) {
        UserRole userRole = findUserRoleByUserRoleId(user.getUserRoleId());
        return Set.of(UserRoleType.ADMIN.name(), UserRoleType.OWNER.name()).contains(userRole.getRoleName());
    }

    public boolean hasClientRole(User user) {
        UserRole userRole = findUserRoleByUserRoleId(user.getUserRoleId());
        return userRole.getRoleName().equals(UserRoleType.CLIENT.name());
    }
}
