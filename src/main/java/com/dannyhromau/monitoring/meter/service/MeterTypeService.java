package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterType;

import java.util.List;

public interface MeterTypeService {
    MeterType getMeterById(long id) throws EntityNotFoundException;
    List<MeterType> getAll();
    MeterType add(MeterType meterType) throws DuplicateDataException;
    long deleteMeter(long id);
    MeterType getMeterByType(String type) throws EntityNotFoundException;
}
