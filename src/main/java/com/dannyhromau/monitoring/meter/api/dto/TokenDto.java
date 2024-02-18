package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
