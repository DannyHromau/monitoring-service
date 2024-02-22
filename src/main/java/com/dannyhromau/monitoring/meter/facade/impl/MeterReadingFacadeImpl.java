package com.dannyhromau.monitoring.meter.facade.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.annotation.Auditable;
import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import com.dannyhromau.monitoring.meter.mapper.MeterReadingMapper;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Component
@AspectLogging
@RequiredArgsConstructor
public class MeterReadingFacadeImpl implements MeterReadingFacade {

    private final MeterReadingService service;
    private final MeterReadingMapper mapper;

//    @Auditable
    @Override
    public MeterReadingDto add(MeterReadingDto meterReadingDto) {
        MeterReading meterReading = service.add(mapper.mapToMeterReading(meterReadingDto));
        return mapper.mapToDto(meterReading);
    }

//    @Auditable
    @Override
    public List<MeterReadingDto> getAll() {
        return mapper.mapToListDto(service.getAll());
    }

//    @Auditable
    @Override
    public List<MeterReadingDto> getByUserId(UUID userId) {
        return mapper.mapToListDto(service.getByUserId(userId));
    }

//    @Auditable
    @Override
    public List<MeterReadingDto> getByUserIdAndMeterType(UUID userId, UUID mrTypeId) {
        return mapper.mapToListDto(service.getByUserIdAndMeterType(userId, mrTypeId));
    }

//    @Auditable
    @Override
    public MeterReadingDto getById(UUID id) {
        return mapper.mapToDto(service.getById(id));
    }

//    @Auditable
    @Override
    public MeterReadingDto getActualMeterReading(UUID userId, UUID mrTypeId) {
        return mapper.mapToDto(service.getActualMeterReading(userId, mrTypeId));
    }

//    @Auditable
    @Override
    public MeterReadingDto getMeterReadingByDateAndMeterType(UUID userId, LocalDate date, UUID mrTypeId) {
        return mapper.mapToDto(service.getMeterReadingByDateAndMeterType(userId, date, mrTypeId));
    }

//    @Auditable
    @Override
    public MeterReadingDto getMeterReadingByMonthAndMeterType(UUID userId, YearMonth yearMonth, UUID mrTypeId) {
        return mapper.mapToDto(service.getMeterReadingByMonthAndMeterType(userId, yearMonth, mrTypeId));
    }
}
