package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.Audit;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AuditRepository<T> {
    Optional<T> findById(long id) throws SQLException;

    List<T> findAll() throws SQLException;

    T save(T audit) throws SQLException;

    void deleteById(long id);

    void deleteAll();
}
