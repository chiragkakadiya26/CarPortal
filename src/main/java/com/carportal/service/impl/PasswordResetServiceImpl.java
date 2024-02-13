package com.carportal.service.impl;

import com.carportal.model.PasswordResetToken;
import com.carportal.model.User;
import com.carportal.repository.PasswordResetTokenRepository;
import com.carportal.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordRestToken = passwordResetTokenRepository.findByUser(user);

        if (passwordRestToken != null) {
            passwordRestToken.setToken(passwordToken);
            passwordRestToken.setExpirationTime(passwordRestToken.getTokenExpirationTime());
            passwordResetTokenRepository.save(passwordRestToken);
        } else {
            PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
            passwordResetTokenRepository.save(passwordResetToken);
        }
    }

    public String validatePasswordResetToken(String passwordToken){
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(passwordToken);
        if(passwordResetToken == null){
            return "Invalid verification token";
        }
        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Link already expired, resend link";
        }
        return "valid";
    }

    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void forgotPassword(User user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));

    }
}
