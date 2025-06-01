package com.seumanoel.service;

import com.seumanoel.exception.AuthenticationErrorException;
import com.seumanoel.model.CredentialsDTO;
import com.seumanoel.model.User;
import com.seumanoel.repository.UserRepository;
import com.seumanoel.security.JwtResponse;
import com.seumanoel.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAuthenticate_Success() {
        // Arrange
        CredentialsDTO credentials = new CredentialsDTO("admin", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("test-token");

        // Act
        JwtResponse response = userService.authenticate(credentials);

        // Assert
        assertNotNull(response);
        assertEquals("test-token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testAuthenticate_Failure() {
        // Arrange
        CredentialsDTO credentials = new CredentialsDTO("admin", "wrong-password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.core.AuthenticationException("Authentication failed") {});

        // Act & Assert
        assertThrows(AuthenticationErrorException.class, () -> userService.authenticate(credentials));
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        User savedUser = userService.registerUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("newuser", savedUser.getUsername());
        assertEquals("encoded-password", savedUser.getPassword());
        assertTrue(savedUser.isActive());
        verify(userRepository).save(user);
    }

    @Test
    public void testRegisterUser_UsernameExists() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("existinguser");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(newUser));

    }
}
