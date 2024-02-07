package com.dannyhromau.monitoring.meter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class JdbcUserAudit extends Audit{
    private long auditingEntityId;
    private String action;

    public JdbcUserAudit(long id, LocalDateTime timestamp, long auditingEntityId, String action) {
        super(id, timestamp);
        this.auditingEntityId = auditingEntityId;
        this.action = action;
    }
}
