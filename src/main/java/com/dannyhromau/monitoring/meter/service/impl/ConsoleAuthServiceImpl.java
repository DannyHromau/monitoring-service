package com.dannyhromau.monitoring.meter.service.impl;

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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleAuthServiceImpl implements AuthService<User> {
    private final UserService userService;
    private final AuthorityService authorityService;
    private static final String passwordPatternRegex = "\\S{8,20}";
    private static final String loginRegex = "^[a-zA-Z0-9._-]{3,15}$";
    private static final String WRONG_INPUT_FORMAT_MESSAGE = ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label;
    private static final String WRONG_AUTH_MESSAGE = ErrorMessages.WRONG_AUTH_MESSAGE.label;
    private static final Logger logger = LogManager.getLogger(ConsoleAuthServiceImpl.class);

    public ConsoleAuthServiceImpl(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    //TODO: implement encoding the password
    @Override
    public User register(User user) throws DuplicateDataException, InvalidDataException, SQLException {
        checkValidData(user);
        List<Authority> authorities = new ArrayList<>();
        try {
            Authority authority = authorityService.getAuthorityByName("user");
            authorities.add(authority);
        } catch (EntityNotFoundException | SQLException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        user.setAuthorities(authorities);
        user.setDeleted(false);
        return userService.add(user);
    }

    //TODO: implement matching the password
    @Override
    public User authorize(User user) throws UnAuthorizedException, SQLException{
        try {
            User authUser = userService.getUserByLogin(user.getLogin());
            if (authUser.getPassword().matches(user.getPassword())) {
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
