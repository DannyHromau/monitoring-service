package com.dannyhromau.monitoring.system.meter.mapper;

import com.dannyhromau.monitoring.system.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.system.meter.model.MeterType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MeterTypeMapper {
    MeterTypeMapper INSTANCE = Mappers.getMapper(MeterTypeMapper.class);

    MeterType mapToMeterType(MeterTypeDto dto);

    MeterTypeDto mapToDto(MeterType meterType);

    List<MeterTypeDto> mapToListDto(List<MeterType> meterTypes);
}
