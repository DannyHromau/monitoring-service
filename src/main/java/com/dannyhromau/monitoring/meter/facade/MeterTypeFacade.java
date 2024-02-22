package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @author Daniil Hromau
 * <p>
 * Facade is using as converting layer between controller and service to separate the logic of working with
 * dto and entity and mapper's injection
 */
@Component
public interface MeterTypeFacade {
    MeterTypeDto getMeterById(UUID id);

    List<MeterTypeDto> getAll();

    MeterTypeDto add(MeterTypeDto meterType);

    MeterTypeDto getMeterByType(String type);
}
