package com.dannyhromau.monitoring.meter.security.provider.custom;

import com.dannyhromau.monitoring.meter.security.common.SecurityUrlConfig;
import com.nimbusds.jose.JWSAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
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
@Profile({"test", "local", "prod"})
@ComponentScan(basePackages = "com.dannyhromau.monitoring.meter.security.provider.custom")
public class CustomSecurityConfig {
    private final SecurityUrlConfig securityUrlConfig;
    private static final String SPEC_KEY = "SecretSpecialKeyOauth2.0Jwt256Bites";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] patternsArr = securityUrlConfig.getUrls().toArray(new String[0]);
        http.oauth2Login(Customizer.withDefaults());
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                .oauth2ResourceServer().jwt().decoder(jwtDecoder());
        return http.build();
    }

//    @Bean
//    protected LogoutSuccessHandler getLogoutSuccessHandler() {
//        return (httpServletRequest, httpServletResponse, authentication) -> {
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//        };
//    }

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
