package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import com.dannyhromau.monitoring.meter.model.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public interface AuthService<T> {
    User register(User user) throws DuplicateDataException, InvalidDataException, SQLException, EntityNotFoundException;

    T authorize(User user) throws UnAuthorizedException, SQLException;
}
