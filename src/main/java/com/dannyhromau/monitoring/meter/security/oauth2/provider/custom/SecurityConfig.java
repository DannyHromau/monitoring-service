package com.dannyhromau.monitoring.meter.security.oauth2.provider.custom;

import com.dannyhromau.monitoring.meter.core.config.AppConfig;
import com.nimbusds.jose.JWSAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AppConfig appConfig;
    private static final String SPEC_KEY = "SecretSpecialKeyOauth2.0Jwt256Bites";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] patternsArr = appConfig.getUrls().toArray(new String[0]);
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(patternsArr).permitAll()
                .antMatchers("/api/v1/meter/reading/all").hasAuthority("SCOPE_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                .oauth2ResourceServer().jwt()
                .decoder(jwtDecoder())
        ;
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(
                new SecretKeySpec(SPEC_KEY.getBytes(StandardCharsets.UTF_8),
                        JWSAlgorithm.RS512.getName())).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
