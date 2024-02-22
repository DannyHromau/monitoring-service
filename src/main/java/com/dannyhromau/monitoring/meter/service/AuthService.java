package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService<T> {
    User register(User user);

    T authorize(User user);
}
