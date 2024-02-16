package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Daniil Hromau
 *
 * Facade is using as converting layer between controller and service to separate the logic of working with
 * dto and entity and mapper's injection
 *
 */
@Component
public interface MeterTypeFacade {
    MeterTypeDto getMeterById(long id) throws EntityNotFoundException, SQLException;
    List<MeterTypeDto> getAll() throws SQLException;
    MeterTypeDto add(MeterTypeDto meterType) throws DuplicateDataException, SQLException;
    MeterTypeDto getMeterByType(String type) throws EntityNotFoundException, SQLException;
}
