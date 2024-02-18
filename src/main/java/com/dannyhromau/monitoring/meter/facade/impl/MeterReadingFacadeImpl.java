package com.dannyhromau.monitoring.meter.facade.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.annotation.Auditable;
import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import com.dannyhromau.monitoring.meter.mapper.MeterReadingMapper;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Component
@AspectLogging
@RequiredArgsConstructor
public class MeterReadingFacadeImpl implements MeterReadingFacade {

    private final MeterReadingService service;
    private final MeterReadingMapper mapper;

    @Auditable
    @Override
    public MeterReadingDto add(MeterReadingDto meterReadingDto)
            throws DuplicateDataException, SQLException, InvalidDataException {
        MeterReading meterReading = service.add(mapper.mapToMeterReading(meterReadingDto));
        return mapper.mapToDto(meterReading);
    }

    @Auditable
    @Override
    public List<MeterReadingDto> getAll() throws SQLException {
        return mapper.mapToListDto(service.getAll());
    }

    @Auditable
    @Override
    public List<MeterReadingDto> getByUserId(long userId) throws SQLException {
        return mapper.mapToListDto(service.getByUserId(userId));
    }

    @Auditable
    @Override
    public List<MeterReadingDto> getByUserIdAndMeterType(long userId, long mrTypeId) throws SQLException {
        return mapper.mapToListDto(service.getByUserIdAndMeterType(userId, mrTypeId));
    }

    @Auditable
    @Override
    public MeterReadingDto getById(long id) throws EntityNotFoundException, SQLException {
        return mapper.mapToDto(service.getById(id));
    }

    @Auditable
    @Override
    public MeterReadingDto getActualMeterReading(long userId, long mrTypeId) throws SQLException, EntityNotFoundException {
        return mapper.mapToDto(service.getActualMeterReading(userId, mrTypeId));
    }

    @Auditable
    @Override
    public MeterReadingDto getMeterReadingByDateAndMeterType(long userId, LocalDate date, long mrTypeId)
            throws SQLException, EntityNotFoundException {
        return mapper.mapToDto(service.getMeterReadingByDateAndMeterType(userId, date, mrTypeId));
    }

    @Auditable
    @Override
    public MeterReadingDto getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, long mrTypeId)
            throws SQLException, EntityNotFoundException {
        return mapper.mapToDto(service.getMeterReadingByMonthAndMeterType(userId, yearMonth, mrTypeId));
    }
}
