package com.dannyhromau.monitoring.system.meter.mapper;

import com.dannyhromau.monitoring.system.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.system.meter.model.MeterReading;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MeterReadingMapper {
    MeterReadingMapper INSTANCE = Mappers.getMapper(MeterReadingMapper.class);

    MeterReading mapToMeterReading(MeterReadingDto dto);

    MeterReadingDto mapToDto(MeterReading meterReading);

    List<MeterReadingDto> mapToListDto(List<MeterReading> meterReadingList);
}
