package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.service.AuthService;
import com.dannyhromau.monitoring.meter.service.AuthorityService;
import com.dannyhromau.monitoring.meter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AspectLogging
@RequiredArgsConstructor
public class ConsoleAuthServiceImpl implements AuthService<User> {
    private final UserService userService;
    private final AuthorityService authorityService;
    private static final String passwordPatternRegex = "\\S{8,20}";
    private static final String loginRegex = "^[a-zA-Z0-9._-]{3,15}$";
    private static final String WRONG_INPUT_FORMAT_MESSAGE = ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label;
    private static final String WRONG_AUTH_MESSAGE = ErrorMessages.WRONG_AUTH_MESSAGE.label;
    private static final Logger logger = LogManager.getLogger(ConsoleAuthServiceImpl.class);


    @Override
    public User register(User user)
            throws DuplicateDataException, InvalidDataException, SQLException, EntityNotFoundException {
        checkValidData(user);
        List<Authority> authorities = new ArrayList<>();
        Authority authority = authorityService.getAuthorityByName("user");
        authorities.add(authority);
        user.setAuthorities(authorities);
        user.setDeleted(false);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userService.add(user);
    }

    @Override
    public User authorize(User user) throws UnAuthorizedException, SQLException {
        try {
            User authUser = userService.getUserByLogin(user.getLogin());
            if (authUser.getPassword().matches(DigestUtils.md5Hex(user.getPassword()))) {
                return authUser;
            } else {
                throw new UnAuthorizedException(WRONG_AUTH_MESSAGE);
            }
        } catch (EntityNotFoundException | UnAuthorizedException e) {
            throw new UnAuthorizedException(WRONG_AUTH_MESSAGE);
        }

    }

    private static void checkValidData(User user) throws InvalidDataException {
        if (!user.getLogin().matches(loginRegex) || !user.getPassword().matches(passwordPatternRegex)) {
            throw new InvalidDataException(WRONG_INPUT_FORMAT_MESSAGE);
        }
    }
}