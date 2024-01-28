package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.User;

public interface AuthService<T> {
    boolean register(User user) throws DuplicateDataException, InvalidDataException;

    T authorize(User user) throws UnAuthorizedException;
}
