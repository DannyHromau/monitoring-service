package com.dannyhromau.monitoring.meter.mapper;

import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.api.dto.UserDto;
import com.dannyhromau.monitoring.meter.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {AuthorityMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User mapToUser(UserDto userDto);
    UserDto mapToDto(User user);
    @Mapping(target = "password", ignore = true)
    AuthDto mapToAuthDto(User user);
    User mapToUserFromAuth(AuthDto authDto);
}
