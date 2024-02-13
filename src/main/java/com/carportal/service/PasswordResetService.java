package com.carportal.service;

import com.carportal.model.PasswordResetToken;
import com.carportal.model.User;

import java.util.Optional;

public interface PasswordResetService {

    void createPasswordResetTokenForUser(User user, String passwordToken);
    public String validatePasswordResetToken(String passwordToken);
    Optional<User> findUserByPasswordToken(String passwordResetToken);
    PasswordResetToken findPasswordResetToken(String token);
    public void forgotPassword(User user, String newPassword);

}
