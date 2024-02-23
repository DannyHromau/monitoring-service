package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.audit.module.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.config.ValidatorConfig;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.model.UserAuthority;
import com.dannyhromau.monitoring.meter.security.oauth2.provider.custom.jwt.JWTProvider;
import com.dannyhromau.monitoring.meter.security.oauth2.provider.custom.jwt.JWToken;
import com.dannyhromau.monitoring.meter.service.AuthService;
import com.dannyhromau.monitoring.meter.service.AuthorityService;
import com.dannyhromau.monitoring.meter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AspectLogging
@RequiredArgsConstructor
@Qualifier("token_service")
@Transactional
public class TokenAuthServiceImpl implements AuthService<JWToken> {
    private final UserService userService;
    private final AuthorityService authorityService;
    private final JWTProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidatorConfig validatorConfig;
    private String passwordPatternRegex = "\\S{8,20}";
    private String loginRegex = "^[a-zA-Z0-9._-]{3,15}$";
    private static final String WRONG_INPUT_FORMAT_MESSAGE = ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label;
    private static final String WRONG_AUTH_MESSAGE = ErrorMessages.WRONG_AUTH_MESSAGE.label;

    @Override
    public User register(User user) {
        checkValidData(user);
        List<Authority> authorities = new ArrayList<>();
        Authority authority = authorityService.getAuthorityByName("USER");
        authorities.add(authority);
        List<UserAuthority> userAuthorities = new ArrayList<>();
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setUserAuthorities(AggregateReference.to(authority.getId().toString()));
        userAuthorities.add(userAuthority);
        user.setUserAuthorities(userAuthorities);
        user.setDeleted(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.add(user);
    }

    @Override
    public JWToken authorize(User user) {
        try {
            User authUser = userService.getUserByLogin(user.getLogin());
            if (passwordEncoder.matches(user.getPassword(), authUser.getPassword())) {
                List<UserAuthority> userAuthorities = authUser.getUserAuthorities();
                List<Authority> authorities = new ArrayList<>();
                for (UserAuthority userAuthority : userAuthorities){
                    authorities.add(authorityService.getById(UUID.fromString(Objects.requireNonNull(userAuthority
                                            .getUserAuthorities()
                                            .getId()))));
                }
                List<String> authoritiesStr = authorities.stream()
                        .map(Authority::getName).toList();
                String accessToken = tokenProvider.createToken(authUser.getId(), authUser.getLogin(), authoritiesStr);
                String refreshToken = tokenProvider.refreshToken(authUser.getId());
                return new JWToken(accessToken, refreshToken);
            } else {
                throw new UnAuthorizedException(WRONG_AUTH_MESSAGE);
            }
        } catch (EntityNotFoundException e) {
            throw new UnAuthorizedException(WRONG_AUTH_MESSAGE);
        }
    }

    private void checkValidData(User user) {
        loginRegex = validatorConfig.getLoginPattern() == null ? loginRegex : validatorConfig.getLoginPattern();
        passwordPatternRegex = validatorConfig.getPasswordPattern() == null ?
                passwordPatternRegex : validatorConfig.getPasswordPattern();
        if (!user.getLogin().matches(loginRegex) || !user.getPassword().matches(passwordPatternRegex)) {
            throw new InvalidDataException(WRONG_INPUT_FORMAT_MESSAGE);
        }
    }
}
