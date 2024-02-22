package com.dannyhromau.monitoring.meter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(schema = "monitoring_service", value = "ms_meter_reading")
public class MeterReading {
    @Id
    @Column("id")
    private UUID id;
    @Column("date")
    private LocalDateTime date;
    @Column("value")
    private int value;
    @Column("user_id")
    private UUID userId;
    @Column("meter_type_id")
    private UUID meterTypeId;

}
