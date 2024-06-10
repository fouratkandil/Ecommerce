package com.EcommerceApp.JGFE.services.auth;

import java.io.IOException;
import java.util.List;

import com.EcommerceApp.JGFE.dto.SignupRequest;
import com.EcommerceApp.JGFE.dto.UserCrudDto;
import com.EcommerceApp.JGFE.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserwithEmail(String email);

    void createAdminaccount();

    List<UserCrudDto> allusers();

    boolean deleteUser(Long id);

    UserCrudDto updateUser(Long userId, UserCrudDto userCrudDto) throws IOException;

    UserCrudDto getUserById(Long userId);

}
