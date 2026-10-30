package com.gainsoclock.backend.services.auth;

import com.gainsoclock.backend.configs.jwt.JwtUtil;
import com.gainsoclock.backend.dtos.LoginDto;
import com.gainsoclock.backend.dtos.LoginResponseDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.dtos.SignupDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserRole;
import com.gainsoclock.backend.repositories.UserRepository;
import com.gainsoclock.backend.repositories.UserRoleRepository;
import com.gainsoclock.backend.types.UserRoleType;
import com.gainsoclock.backend.types.UserType;
import com.gainsoclock.backend.utilities.MessageType;
import com.gainsoclock.backend.utilities.ResponseUtility;
import com.gainsoclock.backend.utilities.SecurityUtil;
import com.gainsoclock.backend.utilities.UserUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;
    private final UserUtility userUtility;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseDto signup(SignupDto dto) {

        if (dto == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }

        if (userUtility.isUserExistByUsername(dto.getUsername())){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USERNAME_ALREADY_EXIST);
        }

        try{
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setName(dto.getName());
            user.setNickname(dto.getNickname());
            user.setUserType(UserType.PENDING);
            user.setUserRoleId(userUtility.findUserRoleIdByUserRoleType(UserRoleType.CLIENT));
            user.setJoinedAt(new Date());
            user.setCreatedBy("SYSTEM-ADMIN");
            user.setCreatedAt(new Date());
            user.setLastVisitAt(new Date());
            user.setPassword(securityUtil.hashPassword(dto.getPassword()));
            user.setUpdatedBy("SYSTEM-ADMIN");
            user.setUpdatedAt(new Date());
            userRepository.save(user);
        } catch (Exception e){
            return ResponseUtility.getErrorResponse("E9999", MessageType.ERROR_SOMETHING_WENT_WRONG);
        }

        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_SET_ACCOUNT);
    }

    @Override
    public ResponseDto login(LoginDto dto) {

        if (dto == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }

        User user = userUtility.findByUsername(dto.getUsername());
        if (user == null) {
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        UserRole role = userRoleRepository.findById(user.getUserRoleId()).orElse(null);
        if (role == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_NO_PERMISSION);
        }

        if (!user.getUserType().equals(UserType.APPROVED)){
            return ResponseUtility.getErrorResponse("E0003", MessageType.ERROR_INVALID_USER_TYPE);
        }

        if (!securityUtil.isPasswordMatched(dto.getPassword(), user.getPassword())){
            return ResponseUtility.getErrorResponse("E0004", MessageType.ERROR_INVALID_CREDENTIALS);
        }
        LoginResponseDto loginResponse = LoginResponseBuilder(user.getUsername(), role.getRoleName());

        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_LOGIN, loginResponse);
    }

    private LoginResponseDto LoginResponseBuilder(String username, String role) {
        String token = jwtUtil.generateToken(username, role);
        return new LoginResponseDto(username, token);
    }

}
