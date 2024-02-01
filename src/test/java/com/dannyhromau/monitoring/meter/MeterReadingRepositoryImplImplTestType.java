package com.dannyhromau.monitoring.meter;

import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.impl.MeterReadingRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;

@DisplayName("Testing of console_meter_reading_repo")
public class MeterReadingRepositoryImplImplTestType {
    private MeterReadingRepositoryImpl mrRepo = new MeterReadingRepositoryImpl();
    private MeterReading mr = new MeterReading();
    @BeforeEach
    void setup(){
        mrRepo.deleteAll();
        mrRepo.resetIdGenerator();
        mr.setDate(LocalDateTime.now());
        MeterType mrType = new MeterType();
        mrType.setType("HEATING");
        mr.setMeterReadingType(mrType);
        mr.setValue(1000);
        mr.setUserId(1);
    }

    @Test
    @DisplayName("add meter reading when empty repo")
    void addMeterReadingWhenEmptyRepo(){
        mrRepo.add(mr);
        int expectedSize = 1;
        int actualSize = mrRepo.findAll().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }
    @Test
    @DisplayName("check incrementing id when add meter reading")
    void incrementIdWhenAddMeterReading(){
        mrRepo.add(mr);
        MeterReading mrNext = new MeterReading();
        mrRepo.add(mrNext);
        long expectedId = 2;
        long actualId = mrRepo.findAll().get(1).getId();
        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    @DisplayName("find all meter readings when repo is not empty")
    void findAllMeterReadingsWhenRepoIsNotEmpty(){
      int expectedMrCount = 5;
      for (int i = 0; i < 5; i++){
          mrRepo.add(new MeterReading());
      }
      int actualMrCount = mrRepo.findAll().size();
      Assertions.assertEquals(expectedMrCount, actualMrCount);
    }

    @Test
    @DisplayName("find meter readings by user id when exist")
    void findMeterReadingsByUserIdWhenExist(){
        mrRepo.add(mr);
        int expectedSize = 1;
        int actualSize = mrRepo.findByUserId(mr.getUserId()).size();
        Assertions.assertEquals(expectedSize, actualSize);
    }
    @Test
    @DisplayName("find meter readings by user id when not exist")
    void findMeterReadingsByUserIdWhenNotExist(){
        int expectedSize = 0;
        int actualSize = mrRepo.findByUserId(mr.getUserId()).size();
        Assertions.assertEquals(expectedSize, actualSize);
    }
    @Test
    @DisplayName("find meter reading by user id, data and type when exists")
    void findMeterReadingsByUserIdAndDataAndMeterReadingTypeWhenExists(){
        mrRepo.add(mr);
        YearMonth yearMonth = YearMonth.of(mr.getDate().getYear(), mr.getDate().getMonth());
        MeterReading actualMr = mrRepo.findByUserIdAndMonthAndMeterType(
                mr.getUserId(), yearMonth, mr.getMeterReadingType()).get();
        Assertions.assertEquals(mr, actualMr);
    }
    @Test
    @DisplayName("find actual meter reading by user id when exists")
    void findActualMeterReadingsByUserIdWhenExists(){
        MeterReading expectedMr = mr;
        mrRepo.add(expectedMr);
        MeterReading oldMr = new MeterReading();
        oldMr.setUserId(expectedMr.getUserId());
        oldMr.setDate(mr.getDate().minusYears(1));
        oldMr.setMeterReadingType(mr.getMeterReadingType());
        mrRepo.add(oldMr);
        MeterReading actualMr = mrRepo.findFirstByOrderByDateDesc(mr.getUserId(), mr.getMeterReadingType()).get();
        Assertions.assertEquals(expectedMr, actualMr);
    }

    @Test
    @DisplayName("find meter reading id when exists")
    void findMeterReadingsByIdWhenExists(){
        MeterReading expectedMr = mrRepo.add(mr);
        MeterReading actualMr = mrRepo.findById(mr.getId()).get();
        Assertions.assertEquals(expectedMr, actualMr);
    }
    @Test
    @DisplayName("find meter reading by id and date when exists")
    void findMeterReadingsByIdAndDateWhenExists(){
        MeterReading expectedMr = mrRepo.add(mr);
        MeterReading actualMr = mrRepo.findByUserIdAndDateAndMeterType(
                mr.getUserId(), mr.getDate().toLocalDate(), mr.getMeterReadingType()).get();
        Assertions.assertEquals(expectedMr, actualMr);
    }

}
