package com.dannyhromau.monitoring.system.meter.controller.impl;

import com.dannyhromau.monitoring.system.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.system.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.system.meter.facade.MeterReadingFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class MeterReadingControllerImpl implements MeterReadingController {

    private final MeterReadingFacade meterReadingFacade;

    @Override
    public ResponseEntity<MeterReadingDto> add(@RequestBody @NonNull MeterReadingDto mr) {
        return ResponseEntity.ok(meterReadingFacade.add(mr));
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getAll() {
        return ResponseEntity.ok(meterReadingFacade.getAll());
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserId(@PathVariable UUID userId) {

        return ResponseEntity.ok(meterReadingFacade.getByUserId(userId));
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(@RequestParam UUID userId,
                                                                         @RequestParam UUID meterTypeId) {
        return ResponseEntity.ok(meterReadingFacade.getByUserIdAndMeterType(userId, meterTypeId));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getById(@PathVariable UUID id) {

        return ResponseEntity.ok(meterReadingFacade.getById(id));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getActualMeterReading(
            @RequestParam UUID userId, @RequestParam UUID meterTypeId) {

        return ResponseEntity.ok(meterReadingFacade.getActualMeterReading(userId, meterTypeId));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(
            @RequestParam UUID userId,
            @RequestParam LocalDate date,
            @RequestParam UUID meterTypeId) {
        return ResponseEntity.ok(meterReadingFacade.getMeterReadingByDateAndMeterType(userId, date, meterTypeId));
    }

    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType(
            @RequestParam UUID userId, @RequestParam YearMonth yearMonth, @RequestParam UUID meterTypeId) {
        return ResponseEntity.ok(meterReadingFacade
                .getMeterReadingByMonthAndMeterType(userId, yearMonth, meterTypeId));
    }
}
