package com.dannyhromau.monitoring.system.meter.repository;

import com.dannyhromau.monitoring.system.meter.model.MeterReading;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT * " +
            "FROM ms_meter_reading " +
            "WHERE user_id = :userId " +
            "AND EXTRACT(YEAR FROM date) = :year " +
            "AND EXTRACT(MONTH FROM date) = :month " +
            "AND meter_type_id = :meterTypeId")
    Optional<MeterReading> findByUserIdAndYearAndMonthAndMeterTypeId(
            @Param("userId") UUID userId,
            @Param("year") int year,
            @Param("month") int month,
            @Param("meterTypeId") UUID meterTypeId);

    Optional<MeterReading> findFirstByOrderByDateDesc(UUID userId, UUID meterTypeId);
}
