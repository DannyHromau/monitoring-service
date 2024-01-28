package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.MeterType;

import java.util.List;
import java.util.Optional;

public interface MeterTypeRepository {
    Optional<MeterType> findById(long id);

    List<MeterType> findAll();

    MeterType add(MeterType meterType);

    void deleteById(long id);

    Optional<MeterType> findMeterTypeByType(String name);

    void deleteAll();

    void addAll(List<MeterType> meterTypeList);
}
