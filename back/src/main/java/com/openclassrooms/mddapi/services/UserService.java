package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    JwtResponse addUser (SignupRequest signupRequest);
    UserDto getUserById(Long id);
    User findById(Long id);
    UserDto getUserByLoginAndPass(String login,String pass);
    User getUserByEmail(String email);
    UserDto getUserDtoByEmail(String email);
    UserDto getLoggedUser();
    JwtResponse loginUser(LoginRequest loginRequest);
    UserDto updateUserProfile(String currentUsername, UserUpdateDTO userUpdateDTO);

}
