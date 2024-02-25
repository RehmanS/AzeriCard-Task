package com.bookstore.securityservice.service;

import com.bookstore.securityservice.entities.User;
import com.bookstore.securityservice.repos.UserRepository;
import com.bookstore.securityservice.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        return JwtUserDetails.create(user);
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public UserDetails loadUserByPin(String pin) {
        //User user = userRepository.findById(id).get();
        User user = userRepository.findUserByUsername(pin);
        return JwtUserDetails.create(user);
    }
}
