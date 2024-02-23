package com.dannyhromau.audit.module.service.impl;

import com.dannyhromau.audit.module.model.Audit;
import com.dannyhromau.audit.module.repository.AuditRepository;
import com.dannyhromau.audit.module.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//TODO: add library-core module
@Service
@Transactional
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

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
