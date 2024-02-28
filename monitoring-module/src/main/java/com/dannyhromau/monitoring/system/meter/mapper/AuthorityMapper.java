package com.dannyhromau.monitoring.system.meter.mapper;

import com.dannyhromau.monitoring.system.meter.api.dto.AuthorityDto;
import com.dannyhromau.monitoring.system.meter.model.Authority;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    Authority mapToAuthority(AuthorityDto authorityDto);

    AuthorityDto mapToDto(Authority authority);

    List<AuthorityDto> mapToListAuthorityDto(List<Authority> authorityList);
}
