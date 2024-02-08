package com.dannyhromau.monitoring.meter.api.dto;

import com.dannyhromau.monitoring.meter.model.MeterType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeterReadingDto {
    private Long id;
    private MeterTypeDto meterType;
    private LocalDateTime date;
    private int value;
    private Long userId;
    private Long meterTypeId;
}
