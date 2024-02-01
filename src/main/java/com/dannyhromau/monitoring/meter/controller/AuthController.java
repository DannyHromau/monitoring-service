package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.model.User;

public interface AuthController<T> {
    ResponseEntity<Boolean> register(User user);

    ResponseEntity<T> authorize(User user);
}
