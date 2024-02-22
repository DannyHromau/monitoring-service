package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID id;
    private String login;
    private String password;
    private boolean isDeleted;
}
