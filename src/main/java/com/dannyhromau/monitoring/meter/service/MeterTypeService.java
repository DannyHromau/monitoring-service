package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterType;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface MeterTypeService {
    MeterType getMeterById(long id) throws EntityNotFoundException, SQLException;

    List<MeterType> getAll() throws SQLException;

    MeterType add(MeterType meterType) throws DuplicateDataException, SQLException;

    long deleteMeter(long id) throws SQLException, EntityNotFoundException;

    MeterType getMeterByType(String type) throws EntityNotFoundException, SQLException;
}
