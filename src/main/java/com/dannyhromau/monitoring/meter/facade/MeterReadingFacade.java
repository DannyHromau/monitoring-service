package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * @author Daniil Hromau
 *
 * Facade is using as converting layer between controller and service to separate the logic of working with
 * dto and entity and mapper's injection
 *
 */
@Component
public interface MeterReadingFacade {

    MeterReadingDto add(MeterReadingDto meterReadingDto)
            throws DuplicateDataException, SQLException, InvalidDataException;
    List<MeterReadingDto> getAll() throws SQLException;
    List<MeterReadingDto> getByUserId(long userId) throws SQLException;
    List<MeterReadingDto> getByUserIdAndMeterType(long userId, long mrTypeId) throws SQLException;
    MeterReadingDto getById(long id) throws EntityNotFoundException, SQLException;

    /**
     * getting actual meter reading
     * @param userId
     * @param mrTypeId meter type id
     * @return last posted meter reading
     * @throws EntityNotFoundException when data don't exist
     * @throws SQLException
     */
    MeterReadingDto getActualMeterReading(long userId, long mrTypeId) throws EntityNotFoundException, SQLException;

    /**
     * getting meter reading by date
     * @param userId
     * @param date LocalDate standard form
     * @param mrTypeId meter type id
     * @return meter reading
     * @throws EntityNotFoundException when data don't exist
     * @throws SQLException
     */
    MeterReadingDto getMeterReadingByDateAndMeterType(long userId, LocalDate date, long mrTypeId)
            throws EntityNotFoundException, SQLException;

    /**
     * getting meter reading by year and month
     * @param userId
     * @param yearMonth format
     * @param mrTypeId
     * @return
     * @throws EntityNotFoundException
     * @throws SQLException
     */
    MeterReadingDto getMeterReadingByMonthAndMeterType(long userId, YearMonth yearMonth, long mrTypeId)
            throws EntityNotFoundException, SQLException;
}
