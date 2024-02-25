package com.dannyhromau.monitoring.system.meter.service.impl;

import com.dannyhromau.monitoring.system.meter.core.config.ValidatorConfig;
import com.dannyhromau.monitoring.system.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.system.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.system.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.system.meter.model.Authority;
import com.dannyhromau.monitoring.system.meter.model.User;
import com.dannyhromau.monitoring.system.meter.model.UserAuthority;
import com.dannyhromau.monitoring.system.meter.repository.UserAuthorityRepository;
import com.dannyhromau.monitoring.system.meter.security.oauth2.provider.custom.jwt.JWTProvider;
import com.dannyhromau.monitoring.system.meter.security.oauth2.provider.custom.jwt.JWToken;
import com.dannyhromau.monitoring.system.meter.service.AuthService;
import com.dannyhromau.monitoring.system.meter.service.AuthorityService;
import com.dannyhromau.monitoring.system.meter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenAuthServiceImpl implements AuthService<JWToken> {
    private final UserService userService;
    private final AuthorityService authorityService;
    private final UserAuthorityRepository userAuthorityRepository;
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
        user.setAuthorities(authorities);
        user.setDeleted(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.add(user);
    }

    @Override
    public JWToken authorize(User user) {
        try {
            User authUser = userService.getUserByLogin(user.getLogin());
            if (passwordEncoder.matches(user.getPassword(), authUser.getPassword())) {
                List<UserAuthority> userAuthorities = userAuthorityRepository
                        .findUserAuthoritiesByUserId(authUser.getId());
                List<Authority> authorities = new ArrayList<>();
                for (UserAuthority userAuthority : userAuthorities){
                    authorities.add(authorityService.getById(userAuthority.getAuthorityId()));
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
