package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.core.util.ErrorStatusBuilder;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@AspectLogging
@RequiredArgsConstructor
public class MeterTypeControllerImpl implements MeterTypeController {
    private final MeterTypeFacade meterTypeFacade;
    private static final String STATUS_OK = "ok";

    @Override
    public ResponseEntity<MeterTypeDto> getMeterById(long id) {
        try {
            return ResponseEntity.of(meterTypeFacade.getMeterById(id), STATUS_OK);
        } catch (EntityNotFoundException | SQLException e) {
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<List<MeterTypeDto>> getAll() {
        try {
            return ResponseEntity.of(meterTypeFacade.getAll(), STATUS_OK);
        } catch (SQLException e) {
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<MeterTypeDto> add(MeterTypeDto meterType) {
        try {
            return ResponseEntity.of(meterTypeFacade.add(meterType), STATUS_OK);
        } catch (DuplicateDataException | SQLException e) {
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<MeterTypeDto> getMeterByType(String type) {
        try {
            return ResponseEntity.of(meterTypeFacade.getMeterByType(type), STATUS_OK);
        } catch (EntityNotFoundException | SQLException e) {
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }
}
