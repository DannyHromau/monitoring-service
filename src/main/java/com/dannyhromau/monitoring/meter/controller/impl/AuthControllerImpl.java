package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.JdbcUserAudit;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.service.AuditService;
import com.dannyhromau.monitoring.meter.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;

//TODO: add facade layer
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController<User> {
    private final AuthService<User> authService;
    private final AuditService<JdbcUserAudit> auditService;
    private static final String STATUS_OK = "ok";
    private static final String STATUS_UNAUTHORIZED = "unauthorized";
    private static final String STATUS_SERVER_ERROR = "internal server error";
    private static final Logger logger = LogManager.getLogger(AuthControllerImpl.class);


    @Override
    public ResponseEntity<Boolean> register(User user) {
        ResponseEntity<Boolean> re = new ResponseEntity<>();
        String loggingTheme = "called register status: ";
        JdbcUserAudit audit = new JdbcUserAudit();
        try {
            authService.register(user);
            re.setBody(true);
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + user.getId());
            audit.setTimestamp(LocalDateTime.now());
            audit.setAuditingEntityId(user.getId());
            audit.setAction(loggingTheme + re.getSystemMessage());
        } catch (DuplicateDataException | InvalidDataException e) {
            re.setBody(false);
            re.setSystemMessage(e.getMessage());
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage());
        } catch (SQLException e) {
            re.setBody(false);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            logger.log(Level.DEBUG, loggingTheme + re.getSystemMessage());
        } finally {
            try {
                auditService.add(audit);
            }
            catch (SQLException e) {
                logger.log(Level.ERROR, loggingTheme + e.getMessage());
            }
        }
        return re;
    }

    @Override
    public ResponseEntity<User> authorize(User user) {
        ResponseEntity<User> re = new ResponseEntity<>();
        String loggingTheme = "called login status: ";
        JdbcUserAudit audit = new JdbcUserAudit();
        try {
            re.setBody(authService.authorize(user));
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + user.getId());
            audit.setTimestamp(LocalDateTime.now());
            audit.setAuditingEntityId(user.getId());
            audit.setAction(loggingTheme + re.getSystemMessage());
        } catch (UnAuthorizedException e) {
            re.setBody(user);
            re.setSystemMessage(e.getMessage());
            logger.log(Level.DEBUG, loggingTheme
                    + STATUS_UNAUTHORIZED
                    + " with login: "
                    + user.getLogin()
                    + " "
                    + re.getSystemMessage());
        } catch (SQLException e) {
            re.setBody(user);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            logger.log(Level.DEBUG, loggingTheme + re.getSystemMessage());
        } finally {
            try {
                auditService.add(audit);
            }
            catch (SQLException e) {
                logger.log(Level.ERROR, loggingTheme + e.getMessage());
            }
        }
        return re;
    }
}
