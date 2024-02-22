package com.dannyhromau.monitoring.meter.facade.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import com.dannyhromau.monitoring.meter.mapper.MeterTypeMapper;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.service.MeterTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AspectLogging
@RequiredArgsConstructor
public class MeterTypeFacadeImpl implements MeterTypeFacade {
    private final MeterTypeService service;
    private final MeterTypeMapper mapper;

    @Override
    public MeterTypeDto getMeterById(UUID id) {
        return mapper.mapToDto(service.getMeterById(id));
    }

    @Override
    public List<MeterTypeDto> getAll() {
        return mapper.mapToListDto(service.getAll());
    }

    @Override
    public MeterTypeDto add(MeterTypeDto meterTypeDto) {
        MeterType meterType = service.add(mapper.mapToMeterType(meterTypeDto));
        return mapper.mapToDto(meterType);
    }

    @Override
    public MeterTypeDto getMeterByType(String type) {
        return mapper.mapToDto(service.getMeterByType(type));
    }
}
