package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.service.AuthService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthControllerImpl implements AuthController<User> {
    private final AuthService<User> authService;
    private static final String STATUS_OK = "ok";
    private static final String STATUS_UNAUTHORIZED = "unauthorized";
    private static final Logger logger = LogManager.getLogger(AuthControllerImpl.class);

    public AuthControllerImpl(AuthService<User> authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<Boolean> register(User user) {
        ResponseEntity<Boolean> re = new ResponseEntity<>();
        String loggingTheme = "called register status: ";
        try {
            re.setBody(authService.register(user));
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + user.getId());
        } catch (DuplicateDataException | InvalidDataException e) {
            re.setBody(false);
            re.setSystemMessage(e.getMessage());
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage());
        }
        return re;
    }

    @Override
    public ResponseEntity<User> authorize(User user) {
        ResponseEntity<User> re = new ResponseEntity<>();
        String loggingTheme = "called login status: ";
        try {
            re.setBody(authService.authorize(user));
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + user.getId());
        } catch (UnAuthorizedException e) {
            re.setBody(user);
            re.setSystemMessage(e.getMessage());
            logger.log(Level.DEBUG, loggingTheme
                    + STATUS_UNAUTHORIZED
                    + " with login: "
                    + user.getLogin()
                    + " "
                    + re.getSystemMessage());
        }
        return re;
    }
}
