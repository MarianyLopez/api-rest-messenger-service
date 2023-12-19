package com.api.messengerservice.services;

import com.api.messengerservice.dtos.UserDTO;
import com.api.messengerservice.entities.User;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.repositories.UserRepository;
import com.api.messengerservice.security.JwtUtils;
import com.api.messengerservice.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<Object> login(Map<String,String> loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.get("email"), loginRequest.get("password"))
            );
            String token = JwtUtils.createToken(loginRequest.get("email"));
            return ResponseEntity.ok(JwtUtils.getAuthentication(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error: " + e.getMessage());
        }
    }

    public String signUp(UserDTO userDTO) {
        if (userRepository.findOneByEmail(userDTO.getEmail()).isEmpty()){
            if (userDTO.getRole().toString().equalsIgnoreCase("ADMIN") ){
                if (userDTO.getIdAdmin() != null) {
                    if (userRepository.findById(userDTO.getIdAdmin()).isEmpty()) {
                        throw new DoesNotExistEntityException("You cannot create a user of type admin because the admin with id " + userDTO.getIdAdmin() + " does not exist");
                    } else {
                        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
                        User userToSave = new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), encryptedPassword, Role.ADMIN);
                        userRepository.save(userToSave);
                        return "The user with email " + userDTO.getEmail() + " successfully created";
                    }
                }else {
                    throw new IllegalArgumentException("To create a user with admin role, the ID Admin field cannot be null.");
                }
            }else {
                String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
                User userToSave = new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), encryptedPassword, Role.USER);
                userRepository.save(userToSave);
                return "The user with email " + userDTO.getEmail() + " successfully created";
            }

        }else {
            throw new InvalidCreateEntityException("The user with email " + userDTO.getEmail() + " already exists");
        }

    }
}
