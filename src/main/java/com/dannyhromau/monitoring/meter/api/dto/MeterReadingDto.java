package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeterReadingDto {
    private Long id;
    private MeterTypeDto meterType;
    private LocalDateTime date;
    private int value;
    @NonNull
    @NotBlank(message = "User id must not be null")
    private Long userId;
    @NonNull
    @NotBlank(message = "Meter type id must not be null")
    private Long meterTypeId;
}
