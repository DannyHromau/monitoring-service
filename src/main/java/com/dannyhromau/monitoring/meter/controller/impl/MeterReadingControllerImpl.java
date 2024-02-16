package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.annotation.Auditable;
import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@AspectLogging
@RestController
@RequiredArgsConstructor
public class MeterReadingControllerImpl implements MeterReadingController {

    private final MeterReadingFacade meterReadingFacade;


    @Override
    public ResponseEntity<MeterReadingDto> add(@NonNull MeterReadingDto mr) {
        try {
            return ResponseEntity.ok(meterReadingFacade.add(mr));
        } catch (DuplicateDataException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<List<MeterReadingDto>> getAll() {
        try {
            return ResponseEntity.ok(meterReadingFacade.getAll());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserId(long userId) {
        try {
            return ResponseEntity.ok(meterReadingFacade.getByUserId(userId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(long userId, long meterTypeId) {
        try {
            return ResponseEntity.ok(meterReadingFacade.getByUserIdAndMeterType(userId, meterTypeId));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<MeterReadingDto> getById(long id) {
        try {
            return ResponseEntity.ok(meterReadingFacade.getById(id));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<MeterReadingDto> getActualMeterReading(long userId, long mrTypeId) {
        try {
            return ResponseEntity.ok(meterReadingFacade.getActualMeterReading(userId, mrTypeId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(
            long userId, LocalDate date, long mrTypeId) {
        try {
            return ResponseEntity.ok(meterReadingFacade.getMeterReadingByDateAndMeterType(userId, date, mrTypeId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Auditable
    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType(
            long userId, YearMonth yearMonth, long mrTypeId) {
        try {
            return ResponseEntity.ok(meterReadingFacade
                    .getMeterReadingByMonthAndMeterType(userId, yearMonth, mrTypeId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
