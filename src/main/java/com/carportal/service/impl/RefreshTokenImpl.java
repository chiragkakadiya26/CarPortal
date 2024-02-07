package com.carportal.service.impl;

import com.carportal.model.RefreshToken;
import com.carportal.model.User;
import com.carportal.repository.RefreshTokenRepository;
import com.carportal.repository.UserRepository;
import com.carportal.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenImpl implements RefreshTokenService {

    public long refreshTokenValidity = 2*60*1000;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Override
    public RefreshToken createRefreshToken(String username) {
        User user = new User();
        user = userRepository.findByEmail(username);

         RefreshToken refreshToken = user.getRefreshToken();

         if(refreshToken == null){

             refreshToken = RefreshToken.builder()
                     .refreshToken(UUID.randomUUID().toString())
                     .expiry(Instant.now().plusMillis(refreshTokenValidity))
                     .user(this.userRepository.findByEmail(user.getEmail()))
                     .build();
         } else {
             refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
         }

         user.setRefreshToken(refreshToken);

        this.refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {

        RefreshToken RefreshTokenOb = this.refreshTokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(()-> new RuntimeException("Given Token does not exists in db"));

        if(RefreshTokenOb.getExpiry().compareTo(Instant.now())<0) {

            //Token Expire then Delete a Database
            this.refreshTokenRepository.delete(RefreshTokenOb);

            throw new RuntimeException("Refresh Token Expired !!");
        }

        return RefreshTokenOb;
    }
}
