package com.carportal.repository;

import com.carportal.model.PasswordResetToken;
import com.carportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String passwordResetToken);
    PasswordResetToken findByUser(User user);
}
