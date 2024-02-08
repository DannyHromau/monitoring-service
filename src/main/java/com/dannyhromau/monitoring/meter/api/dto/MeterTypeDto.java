package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@Setter
public class MeterTypeDto {
    private Long id;
    @NotBlank(message = "Type must not be blank")
    private String type;
}
