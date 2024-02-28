package com.dannyhromau.monitoring.system.meter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(schema = "monitoring_service", value = "ms_user_authority")
public class UserAuthority {
    @Id
    @Column("id")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("authority_id")
    private UUID authorityId;

    public UserAuthority(UUID userId, UUID authorityId) {
        this.userId = userId;
        this.authorityId = authorityId;
    }
}
