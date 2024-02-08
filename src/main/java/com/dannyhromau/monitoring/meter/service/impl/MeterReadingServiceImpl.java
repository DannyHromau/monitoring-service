package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AspectLogging
@RequiredArgsConstructor
public class MeterReadingServiceImpl implements MeterReadingService {
    private final MeterReadingRepository mrRepo;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
    private static final String NO_ACTUAL_METER_READING_MESSAGE = ErrorMessages.NO_ACTUAL_DATA_MESSAGE.label;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String WRONG_VALUE_MESSAGE = ErrorMessages.WRONG_VALUE_MESSAGE.label;


    @Override
    public MeterReading add(MeterReading mr) throws DuplicateDataException, SQLException, InvalidDataException {
        mr.setDate(LocalDateTime.now());
        YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndMonthAndMeterType(
                mr.getUserId(), yearMonth, mr.getMeterTypeId());
        try {
            MeterReading actual = getActualMeterReading(mr.getUserId(), mr.getMeterTypeId());
            if (actual.getValue() > mr.getValue()) {
                throw new InvalidDataException(String.format(WRONG_VALUE_MESSAGE, mr.getValue()));
            }
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (mrOpt.isEmpty()) {
            mrRepo.save(mr);
        } else {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        }
        return mr;
    }

    @Override
    public List<MeterReading> getAll() throws SQLException {
        return mrRepo.findAll();
    }

    @Override
    public List<MeterReading> getByUserId(long userId) throws SQLException {
        return mrRepo.findByUserId(userId);
    }

    @Override
    public List<MeterReading> getByUserIdAndMeterType(long userId, long meterTypeId) throws SQLException {
        return mrRepo.findByUserIdAndMeterType(userId, meterTypeId);
    }

    @Override
    public MeterReading getById(long id) throws EntityNotFoundException, SQLException {
        return mrRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public MeterReading getActualMeterReading(long userId, long mrTypeId) throws EntityNotFoundException,
            SQLException {
        Optional<MeterReading> mrOpt = mrRepo.findFirstByOrderByDateDesc(userId, mrTypeId);
        return mrOpt.orElseThrow(() -> new EntityNotFoundException(NO_ACTUAL_METER_READING_MESSAGE));
    }

    @Override
    public MeterReading getMeterReadingByDateAndMeterType(long userId, LocalDate date, long mrTypeId)
            throws EntityNotFoundException, SQLException {
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndDateAndMeterType(userId, date, mrTypeId);
        return mrOpt.orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "date", date)));
    }

    @Override
    public MeterReading getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, long mrTypeId)
            throws EntityNotFoundException, SQLException {
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndMonthAndMeterType(userId, yearMonth, mrTypeId);
        return mrOpt.orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "date",
                        yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")))));
    }
}
