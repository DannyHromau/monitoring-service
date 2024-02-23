package com.dannyhromau.monitoring.meter.controller.impl;


import com.dannyhromau.audit.module.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.api.dto.TokenDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AspectLogging
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController<TokenDto> {
    private final AuthFacade<TokenDto> authFacade;

    @Override
    public ResponseEntity<Boolean> register(@NonNull AuthDto authDto) {
        return ResponseEntity.ok(authFacade.register(authDto));
    }


    @Override
    public ResponseEntity<TokenDto> authorize(@NonNull AuthDto authDto) {
        return ResponseEntity.ok(authFacade.authorize(authDto));
    }
}
