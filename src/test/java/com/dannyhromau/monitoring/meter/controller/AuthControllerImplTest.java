package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.api.dto.TokenDto;
import com.dannyhromau.monitoring.meter.controller.impl.AuthControllerImpl;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of auth_controller")
public class AuthControllerImplTest {
    @Mock
    private AuthFacade<TokenDto> authFacade;
    @InjectMocks
    private AuthControllerImpl authController;


    @Test
    @DisplayName("Register when user not exists")
    void registerWhenUserNotExists() throws InvalidDataException,
            DuplicateDataException,
            SQLException,
            EntityNotFoundException {
        AuthDto authDto = new AuthDto();
        when(authFacade.register(authDto)).thenReturn(true);
        ResponseEntity<Boolean> response = authController.register(authDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(authFacade, times(1)).register(authDto);
    }

    @Test
    @DisplayName("Register when wrong format of credentials")
    void registerWhenWrongCredentialsFormat() throws InvalidDataException,
            DuplicateDataException,
            SQLException,
            EntityNotFoundException {
        AuthDto authDto = new AuthDto();
        when(authFacade.register(authDto))
                .thenThrow(new InvalidDataException(ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label));
        ResponseEntity<Boolean> response = authController.register(authDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(authFacade, times(1)).register(authDto);
    }

    @Test
    @DisplayName("Authorize when valid credentials")
    void authorizeWhenValidCredentials() throws UnAuthorizedException, SQLException {
        AuthDto authDto = new AuthDto();
        TokenDto tokenDto = new TokenDto();
        when(authFacade.authorize(authDto)).thenReturn(tokenDto);
        ResponseEntity<TokenDto> response = authController.authorize(authDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tokenDto, response.getBody());
        verify(authFacade, times(1)).authorize(authDto);
    }

    @Test
    @DisplayName("Authorize when invalid credentials")
    void authorizeWhenInvalidCredentials() throws UnAuthorizedException, SQLException {
        AuthDto authDto = new AuthDto();
        when(authFacade.authorize(authDto))
                .thenThrow(new UnAuthorizedException(ErrorMessages.WRONG_AUTH_MESSAGE.label));
        ResponseEntity<TokenDto> response = authController.authorize(authDto);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(authFacade, times(1)).authorize(authDto);
    }
}
