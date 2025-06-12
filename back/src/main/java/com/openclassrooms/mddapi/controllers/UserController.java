package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("api/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserUpdateDTO userUpdateDTO, Principal principal) {
        try {
            UserDto updatedUser = userService.updateUserProfile(principal.getName(), userUpdateDTO);
            updatedUser.setPassword(null);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour du profil : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok(userService.getLoggedUser());
    }

}
