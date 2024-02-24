package com.dannyhromau.monitoring.system.meter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table(schema = "monitoring_service", value = "ms_authority")
public class Authority {
    @Id
    @Column("id")
    private UUID id;
    @Column("name")
    private String name;
}
