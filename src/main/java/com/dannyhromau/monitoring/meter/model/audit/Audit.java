package com.dannyhromau.monitoring.meter.model.audit;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    private long id;
    private LocalDateTime timestamp;
    private String auditingArgs;
    private String action;

}
