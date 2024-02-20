package com.dannyhromau.monitoring.meter.service;


import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import com.dannyhromau.monitoring.meter.service.impl.MeterReadingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of console_meter_reading_service")
public class MeterTypeReadingServiceImplTest {

    @Mock
    private MeterReadingRepository mrRepo;
    private MeterReadingService mrService;

    @BeforeEach
    void setUp() {
        mrService = new MeterReadingServiceImpl(mrRepo);
    }


    @Test
    @DisplayName("add meter readings when not exist")
    void addMeterReadingsWhenNotExist() throws DuplicateDataException, SQLException, InvalidDataException {
        int expectedSize = 5;
        List<MeterReading> mrList = new LinkedList<>();
        LocalDateTime date = LocalDateTime.now();
        for (int i = 0; i < expectedSize; i++) {
            MeterReading mr = new MeterReading();
            mr.setUserId(i * 100L);
            MeterType mrType = new MeterType();
            mrType.setId(1L);
            mrType.setType("HEATING");
            mr.setMeterType(mrType);
            mr.setDate(date);
            mr.setMeterTypeId(mrType.getId());
            YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
            doReturn(Optional.empty())
                    .when(mrRepo)
                    .findByUserIdAndMonthAndMeterType(
                            eq(mr.getUserId()),
                            any(YearMonth.class),
                            eq(mr.getMeterType().getId())
                    );
            when(mrRepo.save(mr)).thenReturn(mr);
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
    void addMeterReadingWhenExists() throws SQLException {
        MeterReading mr = new MeterReading();
        mr.setUserId(100L);
        MeterType mrType = new MeterType();
        mrType.setId(1L);
        mrType.setType("HEATING");
        mr.setMeterType(mrType);
        mr.setDate(LocalDateTime.now());
        mr.setMeterTypeId(mrType.getId());
        YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
        when(mrRepo.findByUserIdAndMonthAndMeterType(
                mr.getUserId(), yearMonth, mr.getMeterType().getId()))
                .thenReturn(Optional.of(mr));
        when(mrRepo.findFirstByOrderByDateDesc(mr.getUserId(), mr.getMeterTypeId())).thenReturn(Optional.of(mr));
        assertThatExceptionOfType(DuplicateDataException.class)
                .isThrownBy(() -> mrService.add(mr))
                .withMessage(ErrorMessages.DUPLICATED_DATA_MESSAGE.label);
    }

    @Test
    @DisplayName("get meter readings by user id when exist")
    void getMeterReadingsByUserIdWhenExist() throws SQLException {
        MeterReading mr = new MeterReading();
        mr.setUserId(100L);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterType(mrType);
        mr.setDate(LocalDateTime.now());
        List<MeterReading> expectedList = new ArrayList<>();
        when(mrRepo.findByUserId(mr.getUserId())).thenReturn(expectedList);
        List<MeterReading> actualList = mrService.getByUserId(mr.getUserId());
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("get actual meter reading when not exists")
    void getActualMeterReadingWhenNotExists() throws SQLException {
        MeterReading mr = new MeterReading();
        mr.setId(1L);
        mr.setUserId(100L);
        MeterType mrType = new MeterType();
        mrType.setId(1L);
        mrType.setType("HEATING");
        mr.setMeterType(mrType);
        mr.setDate(LocalDateTime.now());
        mr.setMeterTypeId(mrType.getId());
        when(mrRepo.findFirstByOrderByDateDesc(mr.getUserId(), mr.getMeterType().getId()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> mrService.getActualMeterReading(mr.getUserId(), mr.getMeterType().getId()))
                .withMessage(ErrorMessages.NO_ACTUAL_DATA_MESSAGE.label);
    }

    @Test
    @DisplayName("get meter reading by id when not exists")
    void getMeterReadingByIdWhenNotExists() throws SQLException {
        MeterReading mr = new MeterReading();
        mr.setId(1L);
        mr.setUserId(100L);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterType(mrType);
        mr.setDate(LocalDateTime.now());
        when(mrRepo.findById(mr.getId())).thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> mrService.getById(mr.getId()))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", mr.getId());
    }

    @Test
    @DisplayName("get meter reading by id when exists")
    void getMeterReadingByIdWhenExists() throws EntityNotFoundException, SQLException {
        MeterReading mr = new MeterReading();
        mr.setId(1L);
        mr.setUserId(100L);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterType(mrType);
        mr.setDate(LocalDateTime.now());
        when(mrRepo.findById(mr.getId())).thenReturn(Optional.of(mr));
        MeterReading actualMr = mrService.getById(mr.getId());
        Assertions.assertEquals(mr, actualMr);
    }

    @Test
    @DisplayName("get meter reading by user id and date when exists")
    void getMeterReadingWhenByUserIdAndDateExists() throws EntityNotFoundException, SQLException {
        MeterReading expectedMr = new MeterReading();
        expectedMr.setUserId(100L);
        MeterType mrType = new MeterType();
        mrType.setId(1L);
        mrType.setType("HEATING");
        expectedMr.setMeterType(mrType);
        expectedMr.setDate(LocalDateTime.now());
        doReturn(Optional.of(expectedMr)).when(mrRepo).findByUserIdAndDateAndMeterType(
                expectedMr.getUserId(), expectedMr.getDate().toLocalDate(), expectedMr.getMeterType().getId());
        MeterReading actualMr = mrService.getMeterReadingByDateAndMeterType(
                expectedMr.getUserId(), expectedMr.getDate().toLocalDate(), expectedMr.getMeterType().getId());
        Assertions.assertEquals(expectedMr, actualMr);
    }

    @Test
    @DisplayName("get meter reading by user id and date when not exists")
    void getMeterReadingWhenByUserIdAndDateNotExists() throws SQLException {
        MeterReading mr = new MeterReading();
        mr.setId(1L);
        mr.setUserId(100L);
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mrType.setId(1L);
        mr.setMeterType(mrType);
        mr.setDate(LocalDateTime.now());
        mr.setMeterTypeId(mrType.getId());
        when(mrRepo.findByUserIdAndDateAndMeterType(mr.getUserId(),
                mr.getDate().toLocalDate(),
                mr.getMeterType().getId()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> mrService.getMeterReadingByDateAndMeterType
                        (mr.getUserId(), mr.getDate().toLocalDate(), mr.getMeterType().getId()))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "date",
                        mr.getDate().toLocalDate());
    }
}
