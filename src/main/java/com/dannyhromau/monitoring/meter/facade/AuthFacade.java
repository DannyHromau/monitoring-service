package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;

import java.sql.SQLException;

public interface AuthFacade<T> {
    T register(T authDto) throws DuplicateDataException, SQLException, InvalidDataException, EntityNotFoundException;
    T authorize(T authDto) throws SQLException, UnAuthorizedException;
}
