package com.dannyhromau.monitoring.system.meter.service;

import com.dannyhromau.monitoring.system.meter.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService<T> {
    User register(User user);

    T authorize(User user);
}
