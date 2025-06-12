package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.Exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.Exception.UnauthorizedException;
import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.dtos.UserUpdateDTO;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final  PasswordEncoder passwordEncoder;

    private final TopicServiceImpl topicService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    public UserServiceImpl(UserMapper userMapper, @Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, PasswordEncoder passwordEncoder1, @Lazy TopicServiceImpl topicService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder1;
        this.topicService = topicService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public JwtResponse addUser(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new UnauthorizedException("User already exists");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), signupRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(signupRequest.getEmail());

        return new JwtResponse(jwt, user.getId(), user.getEmail(), user.getName());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with ID : " + id));
        return userMapper.toDto(user, topicService); // <-- Correction ici
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDto getUserByLoginAndPass(String login, String pass) {
        return null;
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user not found : " + email));
        return userMapper.toDto(user, topicService);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user not found : " + email));
        return user;
    }

    @Override
    public UserDto getLoggedUser() {
        // Récupérer l'email de l'utilisateur connecté depuis le SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Le nom correspond à l'email ici
        // Récupérer les informations de l'utilisateur
        return getUserDtoByEmail(email);
    }

    @Override
    public JwtResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(loginRequest.getEmail());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getLastName());
    }

    @Override
    public UserDto updateUserProfile(String currentUsername, UserUpdateDTO userUpdateDTO) {
        User user = (User) userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isEmpty()) {
            user.setEmail(userUpdateDTO.getEmail());
        }

        if (userUpdateDTO.getName() != null && !userUpdateDTO.getName().isEmpty()) {
            user.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        return userMapper.toDto(userRepository.save(user), topicService);
    }

}
