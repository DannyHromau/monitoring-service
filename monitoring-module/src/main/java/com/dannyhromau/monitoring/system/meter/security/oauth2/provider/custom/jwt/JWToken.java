package com.dannyhromau.monitoring.system.meter.security.oauth2.provider.custom.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWToken {
    private String accessToken;
    private String refreshToken;
}
