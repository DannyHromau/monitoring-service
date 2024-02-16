package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterType;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeterTypeRepository {
    Optional<MeterType> findById(long id) throws SQLException;

    List<MeterType> findAll() throws SQLException;

    MeterType save(MeterType meterType) throws SQLException;

    long deleteById(long id) throws SQLException, EntityNotFoundException;

    Optional<MeterType> findMeterTypeByType(String name) throws SQLException;

    void deleteAll();

    void addAll(List<MeterType> meterTypeList) throws SQLException;
}

