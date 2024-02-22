package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.MeterType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeterTypeRepository extends ListCrudRepository<MeterType, UUID> {
    Optional<MeterType> findMeterTypeByType(String name);
}

