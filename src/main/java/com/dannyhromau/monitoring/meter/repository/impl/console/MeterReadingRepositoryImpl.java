package com.dannyhromau.monitoring.meter.repository.impl.console;

import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

public class MeterReadingRepositoryImpl implements MeterReadingRepository {
    private static Map<Long, MeterReading> mrStorage = new HashMap<>();
    private static long generatedId = 1;

    @Override
    public Optional<MeterReading> findFirstByOrderByDateDesc(long userId, long mrTypeId) {
        return findByUserId(userId)
                .stream()
                .filter(m -> m.getMeterType().getId() == mrTypeId)
                .max(Comparator.comparing(MeterReading::getDate));
    }

    @Override
    public MeterReading save(MeterReading mr) {
        mr.setId(generatedId);
        mr.setDate(LocalDateTime.now());
        mrStorage.put(mr.getId(), mr);
        generatedId++;
        return mr;
    }

    @Override
    public List<MeterReading> findAll() {
        return mrStorage.values().stream().toList();
    }

    @Override
    public List<MeterReading> findByUserId(long userId) {
        return mrStorage.values()
                .stream()
                .filter(meterReading -> meterReading.getUserId() == userId)
                .toList();
    }

    @Override
    public List<MeterReading> findByUserIdAndMeterType(long userId, long meterTypeId) {
        return findByUserId(userId)
                .stream()
                .filter(m -> m.getMeterType().getId() == meterTypeId)
                .toList();
    }

    @Override
    public Optional<MeterReading> findById(long id) {
        return Optional.of(mrStorage.get(id));
    }

    @Override
    public Optional<MeterReading> findByUserIdAndDateAndMeterType(
            long userId, LocalDate date, long mrTypeId) {
        return findByUserId(userId)
                .stream()
                .filter(m -> m.getMeterType().getId() == mrTypeId)
                .filter(m -> m.getDate().toLocalDate().equals(date))
                .findFirst();
    }

    @Override
    public Optional<MeterReading> findByUserIdAndMonthAndMeterType(
            long userId, YearMonth yearMonth, long mrTypeId) {
        return findByUserId(userId)
                .stream()
                .filter(m -> m.getMeterType().getId() == mrTypeId)
                .filter(m -> m.getDate().getYear() == yearMonth.getYear())
                .filter(m -> m.getDate().getMonth().equals(yearMonth.getMonth()))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        mrStorage.clear();
    }

    public long resetIdGenerator() {
        return generatedId = 1;
    }
}
