package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.system.meter.Application;
import com.dannyhromau.monitoring.system.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.system.meter.api.dto.TokenDto;
import com.dannyhromau.monitoring.system.meter.controller.impl.AuthControllerImpl;
import com.dannyhromau.monitoring.system.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.system.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.system.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.system.meter.facade.AuthFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest(classes = Application.class)
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
            EntityNotFoundException {
        AuthDto authDto = new AuthDto();
        when(authFacade.register(authDto)).thenReturn(true);
        ResponseEntity<Boolean> response = authController.register(authDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(authFacade, times(1)).register(authDto);
    }

    @Test
    @DisplayName("Authorize when valid credentials")
    void authorizeWhenValidCredentials() throws UnAuthorizedException {
        AuthDto authDto = new AuthDto();
        TokenDto tokenDto = new TokenDto();
        when(authFacade.authorize(authDto)).thenReturn(tokenDto);
        ResponseEntity<TokenDto> response = authController.authorize(authDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tokenDto, response.getBody());
        verify(authFacade, times(1)).authorize(authDto);
    }
}
