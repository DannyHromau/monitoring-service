package com.dannyhromau.monitoring.system.meter.facade.impl;

import com.dannyhromau.monitoring.system.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.system.meter.facade.MeterTypeFacade;
import com.dannyhromau.monitoring.system.meter.mapper.MeterTypeMapper;
import com.dannyhromau.monitoring.system.meter.model.MeterType;
import com.dannyhromau.monitoring.system.meter.service.MeterTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
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
