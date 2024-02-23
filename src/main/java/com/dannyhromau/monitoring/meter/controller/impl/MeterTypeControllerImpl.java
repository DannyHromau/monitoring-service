package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.audit.module.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AspectLogging
@RestController
@RequiredArgsConstructor
public class MeterTypeControllerImpl implements MeterTypeController {
    private final MeterTypeFacade meterTypeFacade;

    @Override
    public ResponseEntity<MeterTypeDto> getMeterById(UUID id) {
        return ResponseEntity.ok(meterTypeFacade.getMeterById(id));
    }

    @Override
    public ResponseEntity<List<MeterTypeDto>> getAll() {
        return ResponseEntity.ok(meterTypeFacade.getAll());
    }

    @Override
    public ResponseEntity<MeterTypeDto> add(@NonNull MeterTypeDto meterType) {
        return ResponseEntity.ok(meterTypeFacade.add(meterType));
    }

    @Override
    public ResponseEntity<MeterTypeDto> getMeterByType(@NonNull String type) {
        return ResponseEntity.ok(meterTypeFacade.getMeterByType(type));
    }
}
