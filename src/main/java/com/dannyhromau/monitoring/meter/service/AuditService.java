package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AuditService<T> {
    T getById(long id) throws EntityNotFoundException, SQLException;

    List<T> getAll() throws SQLException;

    T add(T t) throws SQLException;

    long deleteById(long id) throws SQLException, EntityNotFoundException;
}
