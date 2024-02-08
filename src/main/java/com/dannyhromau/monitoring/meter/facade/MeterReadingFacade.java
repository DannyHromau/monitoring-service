package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.model.MeterReading;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MeterReadingFacade {

    MeterReadingDto add(MeterReadingDto meterReadingDto) throws DuplicateDataException, SQLException, InvalidDataException;
    List<MeterReadingDto> getAll() throws SQLException;
    List<MeterReadingDto> getByUserId(long userId) throws SQLException;
    List<MeterReadingDto> getByUserIdAndMeterType(long userId, long mrTypeId) throws SQLException;
    MeterReadingDto getById(long id) throws EntityNotFoundException, SQLException;
    MeterReadingDto getActualMeterReading(long userId, long mrTypeId) throws EntityNotFoundException, SQLException;
    MeterReadingDto getMeterReadingByDateAndMeterType(long userId, LocalDate date, long mrTypeId)
            throws EntityNotFoundException, SQLException;
    MeterReadingDto getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, long mrTypeId)
            throws EntityNotFoundException, SQLException;
}
