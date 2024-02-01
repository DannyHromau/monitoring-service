package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MeterReadingService {
    MeterReading add(MeterReading mr) throws DuplicateDataException;

    List<MeterReading> getAll();

    List<MeterReading> getByUserId(long userId);

    List<MeterReading> getByUserIdAndMeterType(long userId, MeterType meterType);

    MeterReading getById(long id) throws EntityNotFoundException;

    MeterReading getActualMeterReading(long userId, MeterType mrType) throws EntityNotFoundException;

    MeterReading getMeterReadingByDateAndMeterType(long userId, LocalDate date, MeterType mrType)
            throws EntityNotFoundException;

    MeterReading getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, MeterType mrType)
            throws EntityNotFoundException;
}
