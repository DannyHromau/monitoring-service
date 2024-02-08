package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.api.dto.AuthDto;

public interface AuthController<T> {
    ResponseEntity<Boolean> register(AuthDto user);

    ResponseEntity<T> authorize(AuthDto user);
}
