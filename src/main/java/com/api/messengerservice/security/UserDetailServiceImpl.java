package com.api.messengerservice.security;

import com.api.messengerservice.entities.User;
import com.api.messengerservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository
                .findOneByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User with email " + email + "does not exist"));
       return new UserDetailsImpl(user);
    }
}
