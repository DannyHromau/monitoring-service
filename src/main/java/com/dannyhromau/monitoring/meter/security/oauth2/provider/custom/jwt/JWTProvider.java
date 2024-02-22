package com.dannyhromau.monitoring.meter.security.oauth2.provider.custom.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JWTProvider {
    private static final String DEFAULT_SPEC_KEY = "SecretSpecialKeyOauth2.0Jwt256Bites";
    private int defaultExpirationMinute = 15;
    private int defaultExpirationRefreshHour = 12;
    private String defaultAlgorithm = "HmacSHA256";
    private final JWTConfig jwtConfig;

    public JwtEncoder jwtEncoder() {
        String algorithm = jwtConfig.getAlgorithm() == null ? defaultAlgorithm : jwtConfig.getAlgorithm();
        SecretKey key = new SecretKeySpec(DEFAULT_SPEC_KEY.getBytes(), algorithm);
        JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>(key);
        return new NimbusJwtEncoder(immutableSecret);
    }

    public String createToken(UUID userId, String login, List<String> authorities) {
        int expiration = jwtConfig.getAccessExpiration() == 0 ?
                defaultExpirationMinute : jwtConfig.getAccessExpiration();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .claim("id", userId.toString())
                .claim("login", login)
                .claim("scope", authorities)
                .expiresAt(ZonedDateTime.now().plusMinutes(expiration).toInstant())
                .build();
        JwsAlgorithm jwsAlgorithm = JWSAlgorithm.HS256::getName;

        return jwtEncoder()
                .encode(JwtEncoderParameters.from(JwsHeader.with(jwsAlgorithm).build(), jwtClaimsSet))
                .getTokenValue();

    }

    public String refreshToken(UUID userId) {
        int expiration = jwtConfig.getRefreshExpiration() == 0 ?
                defaultExpirationRefreshHour : jwtConfig.getRefreshExpiration();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .claim("id", userId.toString())
                .expiresAt(ZonedDateTime.now().plusHours(expiration).toInstant())
                .build();
        JwsAlgorithm jwsAlgorithm = JWSAlgorithm.HS256::getName;

        return jwtEncoder()
                .encode(JwtEncoderParameters.from(JwsHeader.with(jwsAlgorithm).build(), jwtClaimsSet))
                .getTokenValue();
    }
}
