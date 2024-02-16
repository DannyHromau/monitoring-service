package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@AspectLogging
@RestController
@RequiredArgsConstructor
public class MeterTypeControllerImpl implements MeterTypeController {
    private final MeterTypeFacade meterTypeFacade;

    @Override
    public ResponseEntity<MeterTypeDto> getMeterById(long id) {
        try {
            return ResponseEntity.ok(meterTypeFacade.getMeterById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<MeterTypeDto>> getAll() {
        try {
            return ResponseEntity.ok(meterTypeFacade.getAll());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<MeterTypeDto> add(@NonNull MeterTypeDto meterType) {
        try {
            return ResponseEntity.ok(meterTypeFacade.add(meterType));
        } catch (DuplicateDataException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<MeterTypeDto> getMeterByType(@NonNull String type) {
        try {
            return ResponseEntity.ok(meterTypeFacade.getMeterByType(type));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
