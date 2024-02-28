package com.dannyhromau.audit.module.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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

    public Audit(LocalDateTime audit_time, String auditingArgs, String audit_action) {
        this.audit_time = audit_time;
        this.auditingArgs = auditingArgs;
        this.audit_action = audit_action;
    }
}