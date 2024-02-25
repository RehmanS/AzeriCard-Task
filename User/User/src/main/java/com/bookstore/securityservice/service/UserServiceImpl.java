package com.bookstore.securityservice.service;

import com.bookstore.securityservice.entities.User;
import com.bookstore.securityservice.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User createUser(User user) {
        return userRepository.save(user);
    }


    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }


    public void delete(long id) {
        userRepository.deleteById(id);
    }


    public User updateUser(User user, long id) {
        Optional<User> user1 = userRepository.findById(id);
        if (user1.isPresent()) {
            User foundUser = user1.get();
            foundUser.setUsername(user.getUsername());
            foundUser.setPassword(user.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        }
        else{
            return null;
        }
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findUserByUsername(userName);
    }
}
