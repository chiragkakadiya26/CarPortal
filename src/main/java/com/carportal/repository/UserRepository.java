package com.carportal.repository;

import com.carportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
    Optional<User> findByUsername(String username);
}
