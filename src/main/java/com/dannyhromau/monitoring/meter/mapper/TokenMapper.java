package com.dannyhromau.monitoring.meter.mapper;

import com.dannyhromau.monitoring.meter.api.dto.TokenDto;
import com.dannyhromau.monitoring.meter.security.oauth2.provider.custom.jwt.JWToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    JWToken mapToToken(TokenDto tokenDto);

    TokenDto mapToDto(JWToken token);
}
