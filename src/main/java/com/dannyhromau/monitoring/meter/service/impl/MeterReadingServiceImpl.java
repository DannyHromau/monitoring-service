package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class MeterReadingServiceImpl implements MeterReadingService {
    private final MeterReadingRepository mrRepo;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
    private static final String NO_ACTUAL_METER_READING_MESSAGE = ErrorMessages.NO_ACTUAL_DATA_MESSAGE.label;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;

    public MeterReadingServiceImpl(MeterReadingRepository mrRepo) {
        this.mrRepo = mrRepo;
    }

    @Override
    public MeterReading add(MeterReading mr) throws DuplicateDataException {
        YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndMonthAndMeterType(
                mr.getUserId(), yearMonth, mr.getMeterReadingType());
        if (mrOpt.isEmpty()) {
            mrRepo.add(mr);
        } else {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        }
        return mr;
    }

    @Override
    public List<MeterReading> getAll() {
        return mrRepo.findAll();
    }

    @Override
    public List<MeterReading> getByUserId(long userId) {
        return mrRepo.findByUserId(userId);
    }

    @Override
    public List<MeterReading> getByUserIdAndMeterType(long userId, MeterType meterType) {
        return mrRepo.findByUserIdAndMeterType(userId, meterType);
    }

    @Override
    public MeterReading getById(long id) throws EntityNotFoundException {
        return mrRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public MeterReading getActualMeterReading(long userId, MeterType mrType) throws EntityNotFoundException {
        Optional<MeterReading> mrOpt = mrRepo.findFirstByOrderByDateDesc(userId, mrType);
        return mrOpt.orElseThrow(() -> new EntityNotFoundException(NO_ACTUAL_METER_READING_MESSAGE));
    }

    @Override
    public MeterReading getMeterReadingByDateAndMeterType(long userId, LocalDate date, MeterType mrType)
            throws EntityNotFoundException {
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndDateAndMeterType(userId, date, mrType);
        return mrOpt.orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "date", date)));
    }

    @Override
    public MeterReading getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, MeterType mrType)
            throws EntityNotFoundException {
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndMonthAndMeterType(userId, yearMonth, mrType);
        return mrOpt.orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "date",
                        yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")))));
    }
}
