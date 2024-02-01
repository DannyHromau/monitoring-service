package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.model.MeterType;

import java.util.List;

public interface MeterTypeController {
    ResponseEntity<MeterType> getMeterById(long id);
    ResponseEntity<List<MeterType>> getAll();
    ResponseEntity<MeterType> add(MeterType meterType);
    ResponseEntity<MeterType> getMeterByType(String type);
}
