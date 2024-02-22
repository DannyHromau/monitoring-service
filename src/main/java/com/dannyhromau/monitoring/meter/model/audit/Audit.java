package com.dannyhromau.monitoring.meter.model.audit;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Table(schema = "audit", value = "ms_audit_user")
public class Audit {
    @Id
    @Column("id")
    private UUID id;
    @Column("audit_time")
    private LocalDateTime audit_time;
    @Column("auditing_args")
    private String auditingArgs;
    @Column("audit_action")
    private String audit_action;
}
