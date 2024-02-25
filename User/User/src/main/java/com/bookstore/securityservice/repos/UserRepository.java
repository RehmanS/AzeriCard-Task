package com.bookstore.securityservice.repos;


import com.bookstore.securityservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String pin);

}
