package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository {
    MeterReading add(MeterReading mr);

    List<MeterReading> findAll();

    List<MeterReading> findByUserId(long userId);

    List<MeterReading> findByUserIdAndMeterType(long userId, MeterType mrType);

    Optional<MeterReading> findById(long id);

    Optional<MeterReading> findByUserIdAndDateAndMeterType(
            long userId, LocalDate date, MeterType mrType);

    Optional<MeterReading> findByUserIdAndMonthAndMeterType(
            long userId, YearMonth yearMonth, MeterType mrType);

    void deleteAll();

    Optional<MeterReading> findFirstByOrderByDateDesc(long userId, MeterType mrType);
}
