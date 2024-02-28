package com.dannyhromau.audit.module.repository;

import com.dannyhromau.audit.module.model.Audit;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditRepository extends ListCrudRepository<Audit, UUID> {
}
