package com.dannyhromau.monitoring.meter.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table(schema = "monitoring_service", value = "ms_user_authority")
public class UserAuthority {
    @Id
    @Column("id")
    private UUID id;
    @Column("authority_id")
    AggregateReference<Authority, String> userAuthorities;
//    @MappedCollection(idColumn = "id", keyColumn = "id")
//    private List<Authority> authorities;
}
