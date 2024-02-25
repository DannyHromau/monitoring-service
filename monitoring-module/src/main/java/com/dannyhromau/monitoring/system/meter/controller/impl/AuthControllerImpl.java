package com.dannyhromau.monitoring.system.meter.controller.impl;


import com.dannyhromau.monitoring.system.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.system.meter.api.dto.TokenDto;
import com.dannyhromau.monitoring.system.meter.controller.AuthController;
import com.dannyhromau.monitoring.system.meter.facade.AuthFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


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
