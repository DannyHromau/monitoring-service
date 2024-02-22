package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.MeterReading;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeterReadingRepository extends ListCrudRepository<MeterReading, UUID> {

    List<MeterReading> findByUserId(UUID userId);

    List<MeterReading> findByUserIdAndMeterTypeId(UUID userId, UUID meterTypeId);

    Optional<MeterReading> findByUserIdAndDateAndMeterTypeId(
            UUID userId, LocalDate date, UUID meterTypeId);

    @Query(value = "SELECT mr " +
            "FROM MeterReading mr " +
            "WHERE mr.user_id=:userId " +
            "and extract(year from date)=:yearMonth.getYear() " +
            "and extract(month from DATE )=:yearMonth.getMonth() " +
            "and mr.meter_type_id=:meterTypeId")
    Optional<MeterReading> findByUserIdAndMonthAndMeterTypeId(
            UUID userId, YearMonth yearMonth, UUID meterTypeId);

    Optional<MeterReading> findFirstByOrderByDateDesc(UUID userId, UUID meterTypeId);
}
