package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface MeterTypeFacade {
    MeterTypeDto getMeterById(long id) throws EntityNotFoundException, SQLException;
    List<MeterTypeDto> getAll() throws SQLException;
    MeterTypeDto add(MeterTypeDto meterType) throws DuplicateDataException, SQLException;
    MeterTypeDto getMeterByType(String type) throws EntityNotFoundException, SQLException;
}
