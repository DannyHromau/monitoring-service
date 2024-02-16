package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.MeterReading;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeterReadingRepository {
    MeterReading save(MeterReading mr) throws SQLException;

    List<MeterReading> findAll() throws SQLException;

    List<MeterReading> findByUserId(long userId) throws SQLException;

    List<MeterReading> findByUserIdAndMeterType(long userId, long meterTypeId) throws SQLException;

    Optional<MeterReading> findById(long id) throws SQLException;

    Optional<MeterReading> findByUserIdAndDateAndMeterType(
            long userId, LocalDate date, long meterTypeId) throws SQLException;

    Optional<MeterReading> findByUserIdAndMonthAndMeterType(
            long userId, YearMonth yearMonth, long meterTypeId) throws SQLException;

    void deleteAll();

    Optional<MeterReading> findFirstByOrderByDateDesc(long userId, long meterTypeId) throws SQLException;
}
