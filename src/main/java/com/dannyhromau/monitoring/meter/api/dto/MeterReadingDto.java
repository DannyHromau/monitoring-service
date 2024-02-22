package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MeterReadingDto {
    private UUID id;
    private LocalDateTime date;
    private int value;
    @NonNull
    @NotBlank(message = "User id must not be null")
    private UUID userId;
    @NonNull
    @NotBlank(message = "Meter type id must not be null")
    private UUID meterTypeId;
}
