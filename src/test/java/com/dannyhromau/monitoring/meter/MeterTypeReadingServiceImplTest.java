package com.dannyhromau.monitoring.meter;


import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import com.dannyhromau.monitoring.meter.repository.impl.MeterReadingRepositoryImpl;
import com.dannyhromau.monitoring.meter.service.MeterReadingService;
import com.dannyhromau.monitoring.meter.service.impl.MeterReadingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Testing of console_meter_reading_service")
public class MeterTypeReadingServiceImplTest {

    private MeterReadingRepository mrRepo = Mockito.mock(MeterReadingRepositoryImpl.class);
    private MeterReadingService mrService = new MeterReadingServiceImpl(mrRepo);


    @Test
    @DisplayName("add meter readings when not exist")
    void addMeterReadingsWhenNotExist() throws DuplicateDataException {
        int expectedSize = 5;
        List<MeterReading> mrList = new LinkedList<>();
        LocalDateTime date = LocalDateTime.now();
        for (int i = 0; i < expectedSize; i++) {
            MeterReading mr = new MeterReading();
            mr.setUserId(i * 100);
            MeterType mrType = new MeterType();
            mrType.setType("HEATING");
            mr.setMeterReadingType(mrType);
            mr.setDate(date);
            YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
            when(mrRepo.findByUserIdAndMonthAndMeterType(
                    mr.getUserId(), yearMonth, mr.getMeterReadingType()))
                    .thenReturn(Optional.empty());
            when(mrRepo.add(mr)).thenReturn(mr);
            mrService.add(mr);
            mrList.add(mr);
            date = date.minusMonths(1);
        }
        when(mrRepo.findAll()).thenReturn(mrList);
        int actualSize = mrService.getAll().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("add meter reading when exists")
    void addMeterReadingWhenExists() {
        MeterReading mr = new MeterReading();
        mr.setUserId(100);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterReadingType(mrType);
        mr.setDate(LocalDateTime.now());
        YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
        when(mrRepo.findByUserIdAndMonthAndMeterType(
                mr.getUserId(), yearMonth, mr.getMeterReadingType()))
                .thenReturn(Optional.of(mr));
        Exception exception = assertThrows(DuplicateDataException.class, () ->
                mrService.add(mr));
        String expectedMessage = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("get meter readings by user id when exist")
    void getMeterReadingsByUserIdWhenExist() {
        MeterReading mr = new MeterReading();
        mr.setUserId(100);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterReadingType(mrType);
        mr.setDate(LocalDateTime.now());
        List<MeterReading> expectedList = new ArrayList<>();
        when(mrRepo.findByUserId(mr.getUserId())).thenReturn(expectedList);
        List<MeterReading> actualList = mrService.getByUserId(mr.getUserId());
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("get actual meter reading when not exists")
    void getActualMeterReadingWhenNotExists() {
        MeterReading mr = new MeterReading();
        mr.setUserId(100);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterReadingType(mrType);
        mr.setDate(LocalDateTime.now());
        when(mrRepo.findFirstByOrderByDateDesc(mr.getId(), mr.getMeterReadingType()))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                mrService.getActualMeterReading(mr.getUserId(), mr.getMeterReadingType()));
        String expectedMessage = ErrorMessages.NO_ACTUAL_DATA_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("get meter reading by id when not exists")
    void getMeterReadingByIdWhenNotExists() {
        MeterReading mr = new MeterReading();
        mr.setId(1);
        mr.setUserId(100);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterReadingType(mrType);
        mr.setDate(LocalDateTime.now());
        when(mrRepo.findById(mr.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                mrService.getById(mr.getId()));
        String expectedMessage = String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", mr.getId());
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("get meter reading by id when exists")
    void getMeterReadingByIdWhenExists() throws EntityNotFoundException {
        MeterReading mr = new MeterReading();
        mr.setId(1);
        mr.setUserId(100);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterReadingType(mrType);
        mr.setDate(LocalDateTime.now());
        when(mrRepo.findById(mr.getId())).thenReturn(Optional.of(mr));
        MeterReading actualMr = mrService.getById(mr.getId());
        Assertions.assertEquals(mr, actualMr);
    }

    @Test
    @DisplayName("get meter reading by user id and date when exists")
    void getMeterReadingWhenByUserIdAndDateExists() throws EntityNotFoundException {
        MeterReading expectedMr = new MeterReading();
        expectedMr.setUserId(100);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        expectedMr.setMeterReadingType(mrType);
        expectedMr.setDate(LocalDateTime.now());
        when(mrRepo.findByUserIdAndDateAndMeterType(
                expectedMr.getUserId(), expectedMr.getDate().toLocalDate(), expectedMr.getMeterReadingType()))
                .thenReturn(Optional.of(expectedMr));
        MeterReading actualMr = mrService.getMeterReadingByDateAndMeterType(
                expectedMr.getUserId(), expectedMr.getDate().toLocalDate(), expectedMr.getMeterReadingType());
        Assertions.assertEquals(expectedMr, actualMr);
    }
    @Test
    @DisplayName("get meter reading by user id and date when not exists")
    void getMeterReadingWhenByUserIdAndDateNotExists() {
        MeterReading mr = new MeterReading();
        mr.setId(1);
        mr.setUserId(100);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterReadingType(mrType);
        mr.setDate(LocalDateTime.now());
        when(mrRepo.findByUserIdAndDateAndMeterType(mr.getUserId(), mr.getDate().toLocalDate(), mr.getMeterReadingType()))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                mrService.getMeterReadingByDateAndMeterType
                        (mr.getUserId(), mr.getDate().toLocalDate(), mr.getMeterReadingType()));
        String expectedMessage = String.format(
                ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "date", mr.getDate().toLocalDate());
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
