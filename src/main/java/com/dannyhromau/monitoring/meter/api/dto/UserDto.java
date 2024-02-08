package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private boolean isDeleted;
}
