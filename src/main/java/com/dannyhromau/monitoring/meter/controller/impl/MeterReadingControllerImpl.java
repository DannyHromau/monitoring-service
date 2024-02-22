package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@AspectLogging
@RestController
@RequiredArgsConstructor
public class MeterReadingControllerImpl implements MeterReadingController {

    private final MeterReadingFacade meterReadingFacade;

    @Override
    public ResponseEntity<MeterReadingDto> add(@NonNull MeterReadingDto mr) {
        return ResponseEntity.ok(meterReadingFacade.add(mr));
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getAll() {
        return ResponseEntity.ok(meterReadingFacade.getAll());
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserId(UUID userId) {

        return ResponseEntity.ok(meterReadingFacade.getByUserId(userId));
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(UUID userId, UUID meterTypeId) {
        return ResponseEntity.ok(meterReadingFacade.getByUserIdAndMeterType(userId, meterTypeId));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getById(UUID id) {

        return ResponseEntity.ok(meterReadingFacade.getById(id));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getActualMeterReading(UUID userId, UUID mrTypeId) {

        return ResponseEntity.ok(meterReadingFacade.getActualMeterReading(userId, mrTypeId));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(
            UUID userId, LocalDate date, UUID mrTypeId) {
        return ResponseEntity.ok(meterReadingFacade.getMeterReadingByDateAndMeterType(userId, date, mrTypeId));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType(
            UUID userId, YearMonth yearMonth, UUID mrTypeId) {
        return ResponseEntity.ok(meterReadingFacade
                .getMeterReadingByMonthAndMeterType(userId, yearMonth, mrTypeId));
    }
}
