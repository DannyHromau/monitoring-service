package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.JdbcUserAudit;
import com.dannyhromau.monitoring.meter.repository.AuditRepository;
import com.dannyhromau.monitoring.meter.service.AuditService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

//TODO implement correct writing user_id when register
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService<JdbcUserAudit> {
    private final AuditRepository<JdbcUserAudit> auditRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    @Override
    public JdbcUserAudit getById(long id) throws EntityNotFoundException, SQLException {
        return auditRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<JdbcUserAudit> getAll() throws SQLException {
        return auditRepository.findAll();
    }

    @Override
    public JdbcUserAudit add(JdbcUserAudit jdbcUserAudit) throws SQLException {
        jdbcUserAudit.setTimestamp(LocalDateTime.now());
            jdbcUserAudit = auditRepository.save(jdbcUserAudit);
        return jdbcUserAudit;
    }

    @Override
    public long deleteById(long id) throws SQLException, EntityNotFoundException {
        auditRepository.deleteById(id);
        return id;
    }
}
