package com.dannyhromau.monitoring.meter.api.dto;

import com.dannyhromau.monitoring.meter.model.Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthDto {
    private Long id;
    private String login;
    private String password;
    private boolean isDeleted;
    private List<Authority> authorities;
}
