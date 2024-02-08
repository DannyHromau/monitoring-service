package com.dannyhromau.monitoring.meter.facade.impl;

import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import com.dannyhromau.monitoring.meter.mapper.MeterReadingMapper;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
public class MeterReadingFacadeImpl implements MeterReadingFacade {

    private final MeterReadingService service;
    private final MeterReadingMapper mapper;


    @Override
    public MeterReadingDto add(MeterReadingDto meterReadingDto) throws DuplicateDataException, SQLException {
        MeterReading meterReading = service.add(mapper.mapToMeterReading(meterReadingDto));
        return mapper.mapToDto(meterReading);
    }

    @Override
    public List<MeterReadingDto> getAll() throws SQLException {
        return mapper.mapToListDto(service.getAll());
    }

    @Override
    public List<MeterReadingDto> getByUserId(long userId) throws SQLException {
        return mapper.mapToListDto(service.getByUserId(userId));
    }

    @Override
    public List<MeterReadingDto> getByUserIdAndMeterType(long userId, long mrTypeId) throws SQLException {
        return mapper.mapToListDto(service.getByUserIdAndMeterType(userId, mrTypeId));
    }

    @Override
    public MeterReadingDto getById(long id) throws EntityNotFoundException, SQLException {
        return mapper.mapToDto(service.getById(id));
    }

    @Override
    public MeterReadingDto getActualMeterReading(long userId, long mrTypeId) throws SQLException, EntityNotFoundException {
        return mapper.mapToDto(service.getActualMeterReading(userId, mrTypeId));
    }

    @Override
    public MeterReadingDto getMeterReadingByDateAndMeterType(long userId, LocalDate date, long mrTypeId)
            throws SQLException, EntityNotFoundException {
        return mapper.mapToDto(service.getMeterReadingByDateAndMeterType(userId, date, mrTypeId));
    }

    @Override
    public MeterReadingDto getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, long mrTypeId)
            throws SQLException, EntityNotFoundException {
        return mapper.mapToDto(service.getMeterReadingByMonthAndMeterType(userId, yearMonth, mrTypeId));
    }
}
