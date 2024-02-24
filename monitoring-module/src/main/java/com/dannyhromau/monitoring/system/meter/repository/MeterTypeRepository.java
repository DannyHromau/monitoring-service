package com.dannyhromau.monitoring.system.meter.repository;

import com.dannyhromau.monitoring.system.meter.model.MeterType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeterTypeRepository extends ListCrudRepository<MeterType, UUID> {
    @Query("SELECT * FROM ms_meter_type WHERE type = :type")
    Optional<MeterType> findMeterTypeByType(@Param("type") String type);
}

