package com.dannyhromau.monitoring.meter.model.audit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserAudit extends Audit {
    private long auditingEntityId;
    private String action;
    @Builder
    public UserAudit(long id, LocalDateTime timestamp, long auditingEntityId, String action) {
        super(id, timestamp);
        this.auditingEntityId = auditingEntityId;
        this.action = action;
    }
}
