package com.dannyhromau.monitoring.system.meter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Table(schema = "monitoring_service", value = "ms_user")
public class User {
    @Id
    @Column("id")
    private UUID id;
    @Column("login")
    private String login;
    @Column("password")
    private String password;
    @Column("is_deleted")
    private boolean isDeleted;
    @Transient
    private List<Authority> authorities;
}
