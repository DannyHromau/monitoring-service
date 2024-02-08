package com.dannyhromau.monitoring.meter.model.audit;

import java.time.LocalDateTime;

public class UserAuditBuilder {
    private long id;
    private LocalDateTime timestamp;
    private String auditingArgs;
    private String action;

    public UserAuditBuilder() {
    }

    public UserAuditBuilder id(long id) {
        this.id = id;
        return this;
    }

    public UserAuditBuilder timestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public UserAuditBuilder auditingArgs(String auditingArgs) {
        this.auditingArgs = auditingArgs;
        return this;
    }

    public UserAuditBuilder action(String action) {
        this.action = action;
        return this;
    }

    public UserAudit build() {
        return new UserAudit(id, timestamp, auditingArgs, action);
    }
}
