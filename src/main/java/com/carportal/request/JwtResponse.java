package com.carportal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String jwtToken;
    private String username;
//    private String refreshToken;
}
