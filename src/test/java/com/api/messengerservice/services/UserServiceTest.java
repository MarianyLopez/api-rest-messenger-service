package com.api.messengerservice.services;

import com.api.messengerservice.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @BeforeEach
    void SetUp() {
        userService = new UserService(userRepository,authenticationManager);
    }
    @Test
    void loginSuccessfully() {
        Map<String,String> map = new HashMap<>();
        map.put("email","example@gmail.com");
        map.put("password","example123");

        when(authenticationManager.authenticate(any())).thenReturn(null);

        ResponseEntity<Object> service = userService.login(map);

        Assertions.assertEquals(HttpStatus.OK,service.getStatusCode());
    }

    @Test
    void loginFailure() {
        Map<String,String> map = new HashMap<>();
        map.put("email","");
        map.put("password","example123");

        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Authentication error") {
        });

        ResponseEntity<Object> service = userService.login(map);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED,service.getStatusCode());
    }
}