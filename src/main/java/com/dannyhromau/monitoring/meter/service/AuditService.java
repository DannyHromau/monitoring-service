package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.audit.Audit;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface AuditService {
    Audit getById(long id) throws EntityNotFoundException, SQLException;

    List<Audit> getAll() throws SQLException;

    Audit add(Audit audit) throws SQLException;

    long deleteById(long id) throws SQLException, EntityNotFoundException;
}
