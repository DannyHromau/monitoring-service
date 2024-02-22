package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.audit.Audit;
import com.dannyhromau.monitoring.meter.repository.AuditRepository;
import com.dannyhromau.monitoring.meter.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AspectLogging
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;

    @Override
    public Audit getById(UUID id) throws EntityNotFoundException {
        return auditRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<Audit> getAll() {
        return auditRepository.findAll();
    }

    @Override
    public Audit add(Audit audit) {
        audit.setAudit_time(LocalDateTime.now());
        audit = auditRepository.save(audit);
        return audit;
    }

    @Override
    public UUID deleteById(UUID id) {
        auditRepository.deleteById(id);
        return id;
    }
}
