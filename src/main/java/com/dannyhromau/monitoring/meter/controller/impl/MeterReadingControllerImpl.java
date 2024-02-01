package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class MeterReadingControllerImpl implements MeterReadingController {
    private final MeterReadingService meterReadingService;
    private static final String STATUS_OK = "ok";
    private static final Logger logger = LogManager.getLogger(MeterReadingControllerImpl.class);

    public MeterReadingControllerImpl(MeterReadingService meterReadingService) {
        this.meterReadingService = meterReadingService;
    }

    @Override
    public ResponseEntity<MeterReading> add(MeterReading mr) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        String loggingTheme = "called add MR status: ";
        try {
            re.setBody(meterReadingService.add(mr));
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme
                    + re.getSystemMessage()
                    + " user: "
                    + mr.getUserId()
                    + " "
                    + re.getBody().getMeterReadingType().getType() + re.getBody().getValue());
        } catch (DuplicateDataException e) {
            re.setBody(mr);
            re.setSystemMessage(e.getMessage());
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + mr.getUserId());
        }
        return re;
    }

    @Override
    public ResponseEntity<List<MeterReading>> getAll() {
        ResponseEntity<List<MeterReading>> re = new ResponseEntity<>();
        re.setBody(meterReadingService.getAll());
        re.setSystemMessage(STATUS_OK);
        return re;
    }

    @Override
    public ResponseEntity<List<MeterReading>> getByUserId(long userId) {
        ResponseEntity<List<MeterReading>> re = new ResponseEntity<>();
        String loggingTheme = "called history status: ";
        re.setBody(meterReadingService.getByUserId(userId));
        re.setSystemMessage(STATUS_OK);
        logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + userId);
        return re;
    }

    @Override
    public ResponseEntity<List<MeterReading>> getByUserIdAndMeterType(long userId, MeterType meterType) {
        ResponseEntity<List<MeterReading>> re = new ResponseEntity<>();
        re.setBody(meterReadingService.getByUserIdAndMeterType(userId, meterType));
        re.setSystemMessage(STATUS_OK);
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getById(long id) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        try {
            re.setBody(meterReadingService.getById(id));
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getActualMeterReading(long userId, MeterType mrType) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        String loggingTheme = "called actual MR status: ";
        try {
            re.setBody(meterReadingService.getActualMeterReading(userId, mrType));
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + userId);
        } catch (EntityNotFoundException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + userId);
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getMeterReadingByDateAndMeterType(
            long userId, LocalDate date, MeterType mrType) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        try {
            re.setBody(meterReadingService.getMeterReadingByDateAndMeterType(userId, date, mrType));
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getMeterReadingByMonthAndMeterType(
            long userId, YearMonth yearMonth, MeterType mrType) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        try {
            re.setBody(meterReadingService.getMeterReadingByMonthAndMeterType(userId, yearMonth, mrType));
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }
}
