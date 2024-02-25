package com.dannyhromau.monitoring.system.meter.facade.impl;

import com.dannyhromau.audit.module.annotation.Auditable;
import com.dannyhromau.monitoring.system.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.system.meter.api.dto.TokenDto;
import com.dannyhromau.monitoring.system.meter.facade.AuthFacade;
import com.dannyhromau.monitoring.system.meter.mapper.TokenMapper;
import com.dannyhromau.monitoring.system.meter.mapper.UserMapper;
import com.dannyhromau.monitoring.system.meter.model.User;
import com.dannyhromau.monitoring.system.meter.security.oauth2.provider.custom.jwt.JWToken;
import com.dannyhromau.monitoring.system.meter.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade<TokenDto> {
    private final AuthService<JWToken> authService;
    private final TokenMapper tokenMapper;
    private final UserMapper userMapper;

    @Auditable
    @Override
    public Boolean register(AuthDto authDto) {
        User user = authService.register(userMapper.mapToUserFromAuth(authDto));
        return user != null;
    }

    @Auditable
    @Override
    public TokenDto authorize(AuthDto authDto) {
        User user = userMapper.mapToUserFromAuth(authDto);
        return tokenMapper.mapToDto(authService.authorize(user));
    }
}
