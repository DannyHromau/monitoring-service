package com.dannyhromau.monitoring.system.meter.security.oauth2.provider.custom.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class JWTConfig {
    private int accessExpiration;
    private int refreshExpiration;
    private String algorithm;
}
