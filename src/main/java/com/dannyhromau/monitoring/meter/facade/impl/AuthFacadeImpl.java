package com.dannyhromau.monitoring.meter.facade.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import com.dannyhromau.monitoring.meter.mapper.UserMapper;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.service.AuthService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@AspectLogging
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade<AuthDto> {
    private final AuthService<User> authService;
    private final UserMapper mapper;

    @Override
    public AuthDto register(AuthDto authDto)
            throws DuplicateDataException, SQLException, InvalidDataException, EntityNotFoundException {
        User user = authService.register(mapper.mapToUserFromAuth(authDto));
        return mapper.mapToAuthDto(user);
    }

    @Override
    public AuthDto authorize(AuthDto authDto) throws SQLException, UnAuthorizedException {
        User user = authService.authorize(mapper.mapToUserFromAuth(authDto));
        return mapper.mapToAuthDto(user);
    }
}
