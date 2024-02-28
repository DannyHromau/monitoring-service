package com.dannyhromau.monitoring.system.meter.facade;

import com.dannyhromau.monitoring.system.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniil Hromau
 * <p>
 * Facade is using as converting layer between controller and service to separate the logic of working with
 * dto and entity and mapper's injection
 */
@Component
public interface MeterReadingFacade {

    MeterReadingDto add(MeterReadingDto meterReadingDto);

    List<MeterReadingDto> getAll();

    List<MeterReadingDto> getByUserId(UUID userId);

    List<MeterReadingDto> getByUserIdAndMeterType(UUID userId, UUID mrTypeId);

    MeterReadingDto getById(UUID id);

    /**
     * getting actual meter reading
     *
     * @param userId
     * @param mrTypeId meter type id
     * @return last posted meter reading
     * @throws EntityNotFoundException when data don't exist
     * @throws SQLException
     */
    MeterReadingDto getActualMeterReading(UUID userId, UUID mrTypeId);

    /**
     * getting meter reading by date
     *
     * @param userId
     * @param date     LocalDate standard form
     * @param mrTypeId meter type id
     * @return meter reading
     * @throws EntityNotFoundException when data don't exist
     * @throws SQLException
     */
    MeterReadingDto getMeterReadingByDateAndMeterType(UUID userId, LocalDate date, UUID mrTypeId);

    /**
     * getting meter reading by year and month
     *
     * @param userId
     * @param yearMonth format
     * @param mrTypeId
     * @return
     * @throws EntityNotFoundException
     * @throws SQLException
     */
    MeterReadingDto getMeterReadingByMonthAndMeterType(UUID userId, YearMonth yearMonth, UUID mrTypeId);
}
