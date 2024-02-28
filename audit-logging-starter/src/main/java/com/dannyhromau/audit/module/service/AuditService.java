package com.dannyhromau.audit.module.service;

import com.dannyhromau.audit.module.model.Audit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AuditService {
    List<Audit> getAll();

    Audit add(Audit audit);

    UUID deleteById(UUID id);
}