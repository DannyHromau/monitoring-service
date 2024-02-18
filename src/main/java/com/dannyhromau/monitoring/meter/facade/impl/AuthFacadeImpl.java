package com.dannyhromau.monitoring.meter.facade.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.annotation.Auditable;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.api.dto.TokenDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import com.dannyhromau.monitoring.meter.mapper.TokenMapper;
import com.dannyhromau.monitoring.meter.mapper.UserMapper;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.security.oauth2.provider.custom.jwt.JWToken;
import com.dannyhromau.monitoring.meter.service.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@AspectLogging
public class AuthFacadeImpl implements AuthFacade<TokenDto> {

    private final AuthService<JWToken> authService;
    private final TokenMapper tokenMapper;
    private final UserMapper userMapper;

    public AuthFacadeImpl(@Qualifier("token_service") AuthService<JWToken> authService,
                          TokenMapper tokenMapper,
                          UserMapper userMapper) {
        this.authService = authService;
        this.tokenMapper = tokenMapper;
        this.userMapper = userMapper;
    }

    @Auditable
    @Override
    public Boolean register(AuthDto authDto)
            throws DuplicateDataException, SQLException, InvalidDataException, EntityNotFoundException {
        User user = authService.register(userMapper.mapToUserFromAuth(authDto));
        return user != null;
    }

    @Auditable
    @Override
    public TokenDto authorize(AuthDto authDto) throws SQLException, UnAuthorizedException {
        User user = userMapper.mapToUserFromAuth(authDto);
        return tokenMapper.mapToDto(authService.authorize(user));
    }
}
