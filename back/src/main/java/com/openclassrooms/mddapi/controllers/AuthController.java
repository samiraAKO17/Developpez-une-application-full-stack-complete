package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api")
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("auth/login")
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginRequest request) {

        JwtResponse response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("auth/register")
    public ResponseEntity<JwtResponse> createUser(@Valid @RequestBody SignupRequest request) {

        JwtResponse response = userService.addUser(request);
        return ResponseEntity.ok(response);

    }
}
