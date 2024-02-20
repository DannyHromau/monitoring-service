package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.audit.Audit;
import com.dannyhromau.monitoring.meter.repository.AuditRepository;
import com.dannyhromau.monitoring.meter.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AspectLogging
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository<Audit> auditRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;

    @Override
    public Audit getById(long id) throws EntityNotFoundException, SQLException {
        return auditRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<Audit> getAll() throws SQLException {
        return auditRepository.findAll();
    }

    @Override
    public Audit add(Audit audit) throws SQLException {
        audit.setTimestamp(LocalDateTime.now());
        audit = auditRepository.save(audit);
        return audit;
    }

    @Override
    public long deleteById(long id) throws SQLException, EntityNotFoundException {
        auditRepository.deleteById(id);
        return id;
    }
}
