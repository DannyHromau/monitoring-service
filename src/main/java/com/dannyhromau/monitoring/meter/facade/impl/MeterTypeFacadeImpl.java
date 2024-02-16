package com.dannyhromau.monitoring.meter.facade.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import com.dannyhromau.monitoring.meter.mapper.MeterTypeMapper;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.service.MeterTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
@AspectLogging
@RequiredArgsConstructor
public class MeterTypeFacadeImpl implements MeterTypeFacade {
    private final MeterTypeService service;
    private final MeterTypeMapper mapper;

    @Override
    public MeterTypeDto getMeterById(long id) throws EntityNotFoundException, SQLException {
        return mapper.mapToDto(service.getMeterById(id));
    }

    @Override
    public List<MeterTypeDto> getAll() throws SQLException {
        return mapper.mapToListDto(service.getAll());
    }

    @Override
    public MeterTypeDto add(MeterTypeDto meterTypeDto) throws DuplicateDataException, SQLException {
        MeterType meterType = service.add(mapper.mapToMeterType(meterTypeDto));
        return mapper.mapToDto(meterType);
    }

    @Override
    public MeterTypeDto getMeterByType(String type) throws EntityNotFoundException, SQLException {
        return mapper.mapToDto(service.getMeterByType(type));
    }
}
