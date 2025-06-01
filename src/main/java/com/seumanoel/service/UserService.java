package com.seumanoel.service;
import com.seumanoel.exception.AuthenticationErrorException;
import com.seumanoel.model.CredentialsDTO;
import com.seumanoel.model.User;
import com.seumanoel.repository.UserRepository;
import com.seumanoel.security.JwtResponse;
import com.seumanoel.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse authenticate(CredentialsDTO credentials) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            return new JwtResponse(jwt);
        } catch (AuthenticationException e) {
            throw new AuthenticationErrorException("Invalid credentials");
        }
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Encode password and set default role if not provided
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singletonList("ROLE_USER"));
        }

        return userRepository.save(user);
    }
}
