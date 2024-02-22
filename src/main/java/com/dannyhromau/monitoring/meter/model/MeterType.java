package com.dannyhromau.monitoring.meter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table(schema = "monitoring_service", value = "ms_meter_type")
public class MeterType {
    @Id
    @Column("id")
    private UUID id;
    @Column("type")
    private String type;
}
