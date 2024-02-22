package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.audit.Audit;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditRepository extends ListCrudRepository<Audit, UUID> {
}
