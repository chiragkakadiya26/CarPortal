package com.carportal.service;

import com.carportal.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);
    RefreshToken verifyRefreshToken(String refreshToken);
}
