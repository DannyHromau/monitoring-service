package com.dannyhromau.monitoring.meter.repository.impl.jdbc;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.audit.UserAudit;
import com.dannyhromau.monitoring.meter.repository.AuditRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@AspectLogging
@RequiredArgsConstructor
public class JdbcUserAuditRepository implements AuditRepository<UserAudit> {
    private final JdbcUtil jdbcUtil;

    @Override
    public Optional<UserAudit> findById(long id) throws SQLException {
        String sql = "SELECT * FROM ms_audit_user WHERE id = ?";
        UserAudit audit = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                audit = UserAudit.builder()
                        .id(rs.getLong("id"))
                        .timestamp(rs.getTimestamp("timestamp").toLocalDateTime())
                        .auditingArgs(rs.getString("auditing_args"))
                        .action(rs.getString("action"))
                        .build();
            }
        }
        if (audit != null) {
            return Optional.of(audit);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<UserAudit> findAll() throws SQLException {
        String sql = "SELECT * FROM ms_audit_user";
        List<UserAudit> audits = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserAudit audit = UserAudit.builder()
                        .id(rs.getLong("id"))
                        .timestamp(rs.getTimestamp("timestamp").toLocalDateTime())
                        .auditingArgs(rs.getString("auditing_args"))
                        .action(rs.getString("action"))
                        .build();
                audits.add(audit);
            }
        }
        return audits;
    }

    @Override
    public UserAudit save(UserAudit audit) throws SQLException {
        String sql = "INSERT INTO ms_audit_user (timestamp, auditing_entity_id, action) VALUES (?,?,?)";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(audit.getTimestamp()));
            stmt.setString(2, audit.getAuditingArgs());
            stmt.setString(3, audit.getAction());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                audit.setId((generatedKeys.getLong(1)));
            }
            return audit;
        }
    }

    //TODO: implement deleting entity by id
    @Override
    public void deleteById(long id) {

    }

    //TODO: implement deleting all entities
    @Override
    public void deleteAll() {

    }
}
