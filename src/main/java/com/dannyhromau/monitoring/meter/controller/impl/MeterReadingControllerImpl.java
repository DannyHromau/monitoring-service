package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.JdbcUserAudit;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.service.AuditService;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

//TODO: add facade layer
@RequiredArgsConstructor
public class MeterReadingControllerImpl implements MeterReadingController {
    private final MeterReadingService meterReadingService;
    private final AuditService<JdbcUserAudit> auditService;
    private static final String STATUS_OK = "ok";
    private static final String STATUS_SERVER_ERROR = "internal server error";
    private static final Logger logger = LogManager.getLogger(MeterReadingControllerImpl.class);

    @Override
    public ResponseEntity<MeterReading> add(MeterReading mr) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        String loggingTheme = "called add MR status: ";
        JdbcUserAudit audit = new JdbcUserAudit();
        try {
                re.setBody(meterReadingService.add(mr));
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme
                    + re.getSystemMessage()
                    + " user: "
                    + mr.getUserId()
                    + " "
                    + re.getBody().getMeterType().getType() + re.getBody().getValue());
            audit.setTimestamp(LocalDateTime.now());
            audit.setAuditingEntityId(mr.getUserId());
            audit.setAction(loggingTheme + re.getSystemMessage());
        } catch (DuplicateDataException e) {
            re.setBody(mr);
            re.setSystemMessage(e.getMessage());
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + mr.getUserId());
        } catch (SQLException e) {
                re.setBody(null);
                re.setSystemMessage(e.getMessage());
        } finally {
            try {
                auditService.add(audit);
            } catch (SQLException e) {
                logger.log(Level.ERROR, loggingTheme + e.getMessage());
            }
        }
        return re;
    }

    @Override
    public ResponseEntity<List<MeterReading>> getAll() {
        ResponseEntity<List<MeterReading>> re = new ResponseEntity<>();
        String loggingTheme = "called get all MR status: ";
        try {
            re.setBody(meterReadingService.getAll());
            re.setSystemMessage(STATUS_OK);
            return re;
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            re.setBody(null);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            return re;
        }
    }

    @Override
    public ResponseEntity<List<MeterReading>> getByUserId(long userId) {
        ResponseEntity<List<MeterReading>> re = new ResponseEntity<>();
        String loggingTheme = "called history status: ";
        try {
            re.setBody(meterReadingService.getByUserId(userId));
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            re.setBody(null);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            return re;
        }
        re.setSystemMessage(STATUS_OK);
        logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + userId);
        return re;
    }

    @Override
    public ResponseEntity<List<MeterReading>> getByUserIdAndMeterType(long userId, long meterTypeId) {
        ResponseEntity<List<MeterReading>> re = new ResponseEntity<>();
        String loggingTheme = "called history status: ";
        JdbcUserAudit audit = new JdbcUserAudit();
        try {
            re.setBody(meterReadingService.getByUserIdAndMeterType(userId, meterTypeId));
            audit.setTimestamp(LocalDateTime.now());
            audit.setAuditingEntityId(userId);
            audit.setAction(loggingTheme + re.getSystemMessage());
            auditService.add(audit);
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            re.setBody(null);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            return re;
        }
        re.setSystemMessage(STATUS_OK);
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getById(long id) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        String loggingTheme = "called get MR by id: ";
        try {
            try {
                re.setBody(meterReadingService.getById(id));
            } catch (SQLException e) {
                logger.log(Level.ERROR, loggingTheme + e.getMessage());
                re.setBody(null);
                re.setSystemMessage(STATUS_SERVER_ERROR);
                return re;
            }
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getActualMeterReading(long userId, long mrTypeId) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        String loggingTheme = "called actual MR status: ";
        JdbcUserAudit audit = new JdbcUserAudit();
        try {
            re.setBody(meterReadingService.getActualMeterReading(userId, mrTypeId));
            re.setSystemMessage(STATUS_OK);
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + userId);
            audit.setTimestamp(LocalDateTime.now());
            audit.setAuditingEntityId(userId);
            audit.setAction(loggingTheme + re.getSystemMessage());
        } catch (EntityNotFoundException e) {
            re.setBody(new MeterReading());
            re.setSystemMessage(e.getMessage());
            logger.log(Level.INFO, loggingTheme + re.getSystemMessage() + " user: " + userId);
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            re.setBody(null);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            return re;
        } finally {
            try {
                auditService.add(audit);
            } catch (SQLException e) {
                logger.log(Level.ERROR, loggingTheme + e.getMessage());
            }
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getMeterReadingByDateAndMeterType(
            long userId, LocalDate date, long mrTypeId) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        String loggingTheme = "called get MR by date and type status: ";
        try {
            re.setBody(meterReadingService.getMeterReadingByDateAndMeterType(userId, date, mrTypeId));
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            re.setBody(null);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            return re;
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterReading> getMeterReadingByMonthAndMeterType(
            long userId, YearMonth yearMonth, long mrTypeId) {
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        String loggingTheme = "called get MR by month and type status: ";
        try {
            re.setBody(meterReadingService.getMeterReadingByMonthAndMeterType(userId, yearMonth, mrTypeId));
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.ERROR, loggingTheme + e.getMessage());
            re.setBody(null);
            re.setSystemMessage(STATUS_SERVER_ERROR);
            return re;
        }
        return re;
    }
}
