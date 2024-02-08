package com.dannyhromau.monitoring.meter.api.dto;

import com.dannyhromau.monitoring.meter.model.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private Long id;
    private String login;
    private String password;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private List<Authority> authorities;
}
