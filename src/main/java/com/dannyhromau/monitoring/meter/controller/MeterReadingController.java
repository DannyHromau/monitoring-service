package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MeterReadingController {
    ResponseEntity<MeterReading> add(MeterReading mr);

    ResponseEntity<List<MeterReading>> getAll();

    ResponseEntity<List<MeterReading>> getByUserId(long userId);
    ResponseEntity<List<MeterReading>> getByUserIdAndMeterType(long userId, long meterTypeId);

    ResponseEntity<MeterReading> getById(long id);

    ResponseEntity<MeterReading> getActualMeterReading(long userId, long meterTypeId);

    ResponseEntity<MeterReading> getMeterReadingByDateAndMeterType(long userId, LocalDate date, long meterTypeId);
    ResponseEntity<MeterReading> getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, long meterTypeId);
}
