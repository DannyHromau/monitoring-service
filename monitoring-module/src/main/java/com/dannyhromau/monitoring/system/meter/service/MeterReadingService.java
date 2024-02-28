package com.dannyhromau.monitoring.system.meter.service;

import com.dannyhromau.monitoring.system.meter.model.MeterReading;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public interface MeterReadingService {
    MeterReading add(MeterReading mr);

    List<MeterReading> getAll();

    List<MeterReading> getByUserId(UUID userId);

    List<MeterReading> getByUserIdAndMeterType(UUID userId, UUID mrTypeId);

    MeterReading getById(UUID id);

    MeterReading getActualMeterReading(UUID userId, UUID mrTypeId);

    MeterReading getMeterReadingByDateAndMeterType(UUID userId, LocalDate date, UUID mrTypeId);

    MeterReading getMeterReadingByMonthAndMeterType(UUID userId, YearMonth yearMonth, UUID mrTypeId);
}
