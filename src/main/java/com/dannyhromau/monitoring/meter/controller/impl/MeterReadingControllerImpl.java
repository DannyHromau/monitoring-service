package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.core.util.ErrorStatusBuilder;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

//TODO: refactor logging
@RequiredArgsConstructor
public class MeterReadingControllerImpl implements MeterReadingController {

    private final MeterReadingFacade meterReadingFacade;
    private static final String STATUS_OK = "ok";
    private static final Logger logger = LogManager.getLogger(MeterReadingControllerImpl.class);

    @Override
    public ResponseEntity<MeterReadingDto> add(MeterReadingDto mr) {
        String loggingTheme = "called add MR status: ";
        try {
            mr = meterReadingFacade.add(mr);
            logger.log(Level.INFO, loggingTheme
                    + STATUS_OK
                    + " user: "
                    + mr.getUserId()
                    + " "
                    + mr.getMeterTypeId() + mr.getValue());
            return ResponseEntity.of(mr, STATUS_OK);
        } catch (DuplicateDataException | SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            return ResponseEntity.of(mr, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getAll() {
        String loggingTheme = "called get all MR status: ";
        try {
            logger.log(Level.INFO, loggingTheme + STATUS_OK);
            return ResponseEntity.of(meterReadingFacade.getAll(), STATUS_OK);
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserId(long userId) {
        String loggingTheme = "called history status: ";
        try {
            logger.log(Level.INFO, userId + " " + loggingTheme + STATUS_OK);
            return ResponseEntity.of(meterReadingFacade.getByUserId(userId), STATUS_OK);
        } catch (SQLException e) {
            logger.log(Level.ERROR, userId + " " + loggingTheme + e.getMessage());
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(long userId, long meterTypeId) {
        String loggingTheme = "called history status: ";
        try {
            logger.log(Level.INFO, loggingTheme + STATUS_OK);
            return ResponseEntity.of(meterReadingFacade.getByUserIdAndMeterType(userId, meterTypeId), STATUS_OK);
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<MeterReadingDto> getById(long id) {
        String loggingTheme = "called get MR by id: ";
        try {
            logger.log(Level.INFO, loggingTheme + id + STATUS_OK);
            return ResponseEntity.of(meterReadingFacade.getById(id), STATUS_OK);
        } catch (SQLException | EntityNotFoundException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<MeterReadingDto> getActualMeterReading(long userId, long mrTypeId) {
        String loggingTheme = "called actual MR status: ";
        try {
            logger.log(Level.INFO, loggingTheme + STATUS_OK + " user: " + userId);
            return ResponseEntity.of(meterReadingFacade.getActualMeterReading(userId, mrTypeId), STATUS_OK);
        } catch (EntityNotFoundException | SQLException e) {
            logger.log(Level.INFO, loggingTheme + e.getMessage() + " user: " + userId);
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(
            long userId, LocalDate date, long mrTypeId) {
        String loggingTheme = "called get MR by date and type status: ";
        try {
            logger.log(Level.INFO, loggingTheme + STATUS_OK + " user: " + userId + " date: " + date.toString());
            return ResponseEntity.of(meterReadingFacade
                    .getMeterReadingByDateAndMeterType(userId, date, mrTypeId), STATUS_OK);
        } catch (EntityNotFoundException | SQLException e) {
            logger.log(Level.INFO, loggingTheme + e.getMessage() + " user: " + userId);
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }

    @Override
    public ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType(
            long userId, YearMonth yearMonth, long mrTypeId) {
        String loggingTheme = "called get MR by month and type status: ";
        try {
            logger.log(Level.INFO,
                    loggingTheme + STATUS_OK + " user: " + userId + " date: " + yearMonth.toString());
            return ResponseEntity.of(meterReadingFacade
                    .getMeterReadingByMonthAndMeterType(userId, yearMonth, mrTypeId), STATUS_OK);
        } catch (EntityNotFoundException | SQLException e) {
            logger.log(Level.INFO, loggingTheme + e.getMessage() + " user: " + userId);
            return ResponseEntity.of(null, ErrorStatusBuilder.getStatus(e));
        }
    }
}
