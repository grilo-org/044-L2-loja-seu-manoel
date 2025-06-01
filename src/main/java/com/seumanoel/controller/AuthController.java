    package com.seumanoel.controller;


    import com.seumanoel.model.CredentialsDTO;
    import com.seumanoel.model.User;
    import com.seumanoel.security.JwtResponse;
    import com.seumanoel.service.UserService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    @Tag(name = "Authentication", description = "API for user authentication and registration")
    public class AuthController {

        private final UserService userService;

        @PostMapping("/login")
        @Operation(summary = "Authenticate user", description = "Authenticates a user and returns a JWT token")
        public ResponseEntity<JwtResponse> login(@Valid @RequestBody CredentialsDTO credentials) {
            JwtResponse jwtResponse = userService.authenticate(credentials);
            return ResponseEntity.ok(jwtResponse);
        }

        @PostMapping("/register")
        @Operation(summary = "Register new user", description = "Creates a new user in the system")
        public ResponseEntity<User> register(@Valid @RequestBody User user) {
            User newUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
    }
