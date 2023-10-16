package com.api.messengerservice.utils;

import com.api.messengerservice.entities.User;
import com.api.messengerservice.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;

    public Runner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0){
            userRepository.save(
                    new User(1234,"admin","admin@gmail.com","$2a$10$pJ7H.65ssjYRhXm8S2k5vOAGUqtgC9hM.SCyklULckvZKz64TV1/O",Role.ADMIN)
            );
        }

    }
}
