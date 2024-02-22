package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.UUID;

@Getter
@Setter
public class MeterTypeDto {
    private UUID id;
    @NotBlank(message = "Type must not be blank")
    private String type;
}
