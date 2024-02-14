package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.annotation.Auditable;
import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.core.util.ErrorStatusBuilder;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

@AspectLogging
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController<AuthDto> {
    private final AuthFacade<AuthDto> authFacade;
    private static final String STATUS_OK = "ok";
    private static final Logger logger = LogManager.getLogger(AuthControllerImpl.class);


   @Auditable
    @Override
    public ResponseEntity<Boolean> register(AuthDto authDto) {
        String loggingTheme = "called register status: ";
        try {
            authDto = authFacade.register(authDto);
            logger.log(Level.INFO, loggingTheme + STATUS_OK+ " user: " + authDto.getId());
            return ResponseEntity.of(true, STATUS_OK);
        } catch (InvalidDataException | DuplicateDataException | SQLException | EntityNotFoundException e) {
            logger.log(Level.INFO, loggingTheme + ErrorStatusBuilder.getStatus(e) + " user: " + authDto.getId());
            return ResponseEntity.of(false, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Auditable
    @Override
    public ResponseEntity<AuthDto> authorize(AuthDto authDto) {
        String loggingTheme = "called login status: ";
        try {
            authDto = authFacade.authorize(authDto);
            logger.log(Level.INFO, loggingTheme + STATUS_OK + " user: " + authDto.getId());
            return ResponseEntity.of(authDto, STATUS_OK);
        } catch (UnAuthorizedException | SQLException e) {
            logger.log(Level.DEBUG, loggingTheme
                    + ErrorStatusBuilder.getStatus(e)
                    + " with login: "
                    + authDto.getLogin());
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }


}
