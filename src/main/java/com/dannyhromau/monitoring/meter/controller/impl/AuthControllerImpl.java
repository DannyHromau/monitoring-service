package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.annotation.Auditable;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@AspectLogging
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController<AuthDto> {
    private final AuthFacade<AuthDto> authFacade;

    @Auditable
    @Override
    public ResponseEntity<Boolean> register(@NonNull AuthDto authDto) {
        try {
            return ResponseEntity.ok(authFacade.register(authDto));
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (DuplicateDataException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<AuthDto> authorize(@NonNull AuthDto authDto) {
        try {
            return ResponseEntity.ok(authFacade.authorize(authDto));
        } catch (UnAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
