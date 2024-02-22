package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.model.audit.Audit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AuditService {
    Audit getById(UUID id);

    List<Audit> getAll();

    Audit add(Audit audit);

    UUID deleteById(UUID id);
}
