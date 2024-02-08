package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.model.MeterReading;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MeterReadingService {
    MeterReading add(MeterReading mr) throws DuplicateDataException, SQLException, InvalidDataException;

    List<MeterReading> getAll() throws SQLException;

    List<MeterReading> getByUserId(long userId) throws SQLException;

    List<MeterReading> getByUserIdAndMeterType(long userId, long mrTypeId) throws SQLException;

    MeterReading getById(long id) throws EntityNotFoundException, SQLException;

    MeterReading getActualMeterReading(long userId, long mrTypeId) throws EntityNotFoundException, SQLException;

    MeterReading getMeterReadingByDateAndMeterType(long userId, LocalDate date, long mrTypeId)
            throws EntityNotFoundException, SQLException;

    MeterReading getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, long mrTypeId)
            throws EntityNotFoundException, SQLException;
}
