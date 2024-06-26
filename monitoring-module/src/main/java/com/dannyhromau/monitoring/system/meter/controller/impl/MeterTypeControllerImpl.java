package com.dannyhromau.monitoring.system.meter.controller.impl;

import com.dannyhromau.monitoring.system.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.system.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.system.meter.facade.MeterTypeFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class MeterTypeControllerImpl implements MeterTypeController {
    private final MeterTypeFacade meterTypeFacade;

    @Override
    public ResponseEntity<MeterTypeDto> getMeterById(@PathVariable UUID id) {
        return ResponseEntity.ok(meterTypeFacade.getMeterById(id));
    }

    @Override
    public ResponseEntity<List<MeterTypeDto>> getAll() {
        return ResponseEntity.ok(meterTypeFacade.getAll());
    }

    @Override
    public ResponseEntity<MeterTypeDto> add(@RequestBody @NonNull MeterTypeDto meterType) {
        return ResponseEntity.ok(meterTypeFacade.add(meterType));
    }

    @Override
    public ResponseEntity<MeterTypeDto> getMeterByType(@PathVariable @NonNull String type) {
        return ResponseEntity.ok(meterTypeFacade.getMeterByType(type));
    }
}
