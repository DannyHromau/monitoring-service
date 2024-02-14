package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface MeterReadingController {
    ResponseEntity<MeterReadingDto> add(MeterReadingDto mr);

    ResponseEntity<List<MeterReadingDto>> getAll();

    ResponseEntity<List<MeterReadingDto>> getByUserId(long userId);
    ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(long userId, long meterTypeId);

    ResponseEntity<MeterReadingDto> getById(long id);

    ResponseEntity<MeterReadingDto> getActualMeterReading(long userId, long meterTypeId);

    ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(long userId, LocalDate date, long meterTypeId);
    ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType
            (long userId, YearMonth yearMonth, long meterTypeId);
}
