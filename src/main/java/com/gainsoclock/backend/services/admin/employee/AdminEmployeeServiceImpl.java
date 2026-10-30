package com.gainsoclock.backend.services.admin.employee;

import com.gainsoclock.backend.dtos.EmployeeDto;
import com.gainsoclock.backend.dtos.EmployeeListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.repositories.UserRepository;
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
public class AdminEmployeeServiceImpl implements AdminEmployeeService {

    private final UserRepository userRepository;
    private final UserUtility userUtility;
    private final SecurityUtil securityUtil;

    @Override
    public ResponseDto setEmployee(EmployeeDto dto) {
        if (dto == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_DTO_IS_NULL_OR_INCOMPLETE);
        }

        Long userId = dto.getUserId() != null ? dto.getUserId() : 0L;

        User user = userRepository.findById(userId).orElse(null);
        try{

            if (user == null){
                user = new User();
                user.setUsername(dto.getUsername());
                user.setName(dto.getName());
                user.setNickname(dto.getNickname());
                user.setUserType(UserType.APPROVED);
                user.setUserRoleId(userUtility.findUserRoleIdByUserRoleType(UserRoleType.EMPLOYEE));
                user.setJoinedAt(new Date());
                user.setCreatedBy("SYSTEM-ADMIN");
                user.setCreatedAt(new Date());
            }
            user.setLastVisitAt(new Date());
            user.setPassword(securityUtil.hashPassword(dto.getPassword()));
            user.setUpdatedBy("SYSTEM-ADMIN");
            user.setUpdatedAt(new Date());
            userRepository.save(user);
        } catch (Exception e){
          return ResponseUtility.getErrorResponse("E9999", MessageType.ERROR_FAILED_TO_CREATE_ACCOUNT);
        }

        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_SET_ACCOUNT);
    }

    @Override
    public ResponseDto listEmployee(EmployeeListDto dto) {
        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_FETCH_USER_DATA, userRepository.findAll());
    }

    @Override
    public ResponseDto deleteEmployeeById(Long id) {

        if (userUtility.findUserById(id).getData() == null){
            return ResponseUtility.getSuccessResponse(MessageType.ERROR_USER_NOT_EXIST);
        }

        userRepository.deleteById(id);
        return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_DELETED_USER);
    }
}
