package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.audit.module.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AspectLogging
@RequiredArgsConstructor
public class MeterReadingServiceImpl implements MeterReadingService {
    private final MeterReadingRepository mrRepo;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
    private static final String NO_ACTUAL_METER_READING_MESSAGE = ErrorMessages.NO_ACTUAL_DATA_MESSAGE.label;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String WRONG_VALUE_MESSAGE = ErrorMessages.WRONG_VALUE_MESSAGE.label;


    @Override
    public MeterReading add(MeterReading mr) {
        mr.setDate(LocalDateTime.now());
        YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndMonthAndMeterTypeId(
                mr.getUserId(), yearMonth, mr.getMeterTypeId());
        try {
            MeterReading actual = getActualMeterReading(mr.getUserId(), mr.getMeterTypeId());
            if (actual.getValue() > mr.getValue()) {
                throw new InvalidDataException(String.format(WRONG_VALUE_MESSAGE, mr.getValue()));
            }
        } catch (EntityNotFoundException e) {
            mrOpt = Optional.empty();
        }
        if (mrOpt.isEmpty()) {
            mrRepo.save(mr);
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
    public List<MeterReading> getByUserId(UUID userId) {
        return mrRepo.findByUserId(userId);
    }

    @Override
    public List<MeterReading> getByUserIdAndMeterType(UUID userId, UUID meterTypeId) {
        return mrRepo.findByUserIdAndMeterTypeId(userId, meterTypeId);
    }

    @Override
    public MeterReading getById(UUID id) {
        return mrRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public MeterReading getActualMeterReading(UUID userId, UUID mrTypeId) {
        Optional<MeterReading> mrOpt = mrRepo.findFirstByOrderByDateDesc(userId, mrTypeId);
        return mrOpt.orElseThrow(() -> new EntityNotFoundException(NO_ACTUAL_METER_READING_MESSAGE));
    }

    @Override
    public MeterReading getMeterReadingByDateAndMeterType(UUID userId, LocalDate date, UUID mrTypeId) {
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndDateAndMeterTypeId(userId, date, mrTypeId);
        return mrOpt.orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "date", date)));
    }

    @Override
    public MeterReading getMeterReadingByMonthAndMeterType(UUID userId, YearMonth yearMonth, UUID mrTypeId) {
        Optional<MeterReading> mrOpt = mrRepo.findByUserIdAndMonthAndMeterTypeId(userId, yearMonth, mrTypeId);
        return mrOpt.orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "date",
                        yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")))));
    }
}
