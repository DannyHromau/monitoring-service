package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.model.MeterType;

import java.util.List;

public interface MeterTypeController {
    ResponseEntity<MeterTypeDto> getMeterById(long id);
    ResponseEntity<List<MeterTypeDto>> getAll();
    ResponseEntity<MeterTypeDto> add(MeterTypeDto meterType);
    ResponseEntity<MeterTypeDto> getMeterByType(String type);
}
