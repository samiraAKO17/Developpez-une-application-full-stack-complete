package com.openclassrooms.mddapi.security.services;

import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  UserRepository userRepository;

  UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByNameOrEmail(username, username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with : " + username));

    return UserDetailsImpl
            .builder()
            .id(user.getId())
            .username(user.getEmail())
            .lastName(user.getName())
            .password(user.getPassword())
            .build();
  }

}
