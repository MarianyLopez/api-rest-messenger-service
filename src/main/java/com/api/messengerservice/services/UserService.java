package com.api.messengerservice.services;

import com.api.messengerservice.dtos.UserDTO;
import com.api.messengerservice.entities.User;
import com.api.messengerservice.exceptions.DoesNotExistEntityException;
import com.api.messengerservice.exceptions.InvalidCreateEntityException;
import com.api.messengerservice.repositories.UserRepository;
import com.api.messengerservice.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                    throw new IllegalArgumentException("To create a user with admin role, the idAdmin field cannot be null.");
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
