package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.model.MeterType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface MeterTypeService {
    MeterType getMeterById(UUID id);

    List<MeterType> getAll();

    MeterType add(MeterType meterType);

    UUID deleteMeter(UUID id);

    MeterType getMeterByType(String type);
}
