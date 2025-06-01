    package com.seumanoel.security;

    import io.jsonwebtoken.Claims;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.test.util.ReflectionTestUtils;

    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Collections;
    import java.util.List;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class JwtTokenProviderTest {

        @InjectMocks
        private JwtTokenProvider jwtTokenProvider;

        @Mock
        private Authentication authentication;

        private UserDetails userDetails;

        @BeforeEach
        public void setup() {
            ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", "testSecretKeyForJwtTokenGenerationAndValidation");
            ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpiration", 3600);

            userDetails = new User("testuser", "password",
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        }

        @Test
        public void testGenerateToken() {
            // Arrange
            when(authentication.getPrincipal()).thenReturn(userDetails);

            // Crie as autoridades
            List<SimpleGrantedAuthority> authList = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_USER")
            );

            // Use doReturn para evitar problemas com tipos genéricos
            doReturn(authList).when(authentication).getAuthorities();

            // Act
            String token = jwtTokenProvider.generateToken(authentication);

            // Assert
            assertNotNull(token);
            assertTrue(token.length() > 0);
        }

        @Test
        public void testValidateToken_ValidToken() {
            // Arrange
            when(authentication.getPrincipal()).thenReturn(userDetails);

            // Crie uma lista concreta de autoridades
            List<SimpleGrantedAuthority> authList = new ArrayList<>();
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));

            // Use doReturn para evitar problemas com tipos genéricos
            doReturn(authList).when(authentication).getAuthorities();

            String token = jwtTokenProvider.generateToken(authentication);

            // Act
            boolean isValid = jwtTokenProvider.validateToken(token);

            // Assert
            assertTrue(isValid);
        }

        @Test
        public void testParseToken() {
            // Arrange
            when(authentication.getPrincipal()).thenReturn(userDetails);

            Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_USER")
            );

            // Use doReturn em vez de when().thenReturn()
            doReturn(authorities).when(authentication).getAuthorities();

            // Generate token
            String token = jwtTokenProvider.generateToken(authentication);

            // Act
            Claims claims = jwtTokenProvider.parseToken(token);

            // Assert
            assertNotNull(claims);
            assertEquals("testuser", claims.getSubject());
        }

        @Test
        public void testGetAuthentication() {
            // Arrange
            when(authentication.getPrincipal()).thenReturn(userDetails);

            Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_USER")
            );

            // Use doReturn em vez de when().thenReturn()
            doReturn(authorities).when(authentication).getAuthorities();

            String token = jwtTokenProvider.generateToken(authentication);

            // Act
            Authentication resultAuth = jwtTokenProvider.getAuthentication(token);

            // Assert
            assertNotNull(resultAuth);
            assertTrue(resultAuth instanceof UsernamePasswordAuthenticationToken);
            assertEquals("testuser", ((UserDetails)resultAuth.getPrincipal()).getUsername());
            assertEquals(1, resultAuth.getAuthorities().size());
            assertTrue(resultAuth.getAuthorities().iterator().next().getAuthority().equals("ROLE_USER"));
        }
    }
