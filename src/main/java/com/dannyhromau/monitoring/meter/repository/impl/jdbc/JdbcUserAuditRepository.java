package com.dannyhromau.monitoring.meter.repository.impl.jdbc;

import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.JdbcUserAudit;
import com.dannyhromau.monitoring.meter.repository.AuditRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcUserAuditRepository implements AuditRepository<JdbcUserAudit> {
    private final JdbcUtil jdbcUtil;

    @Override
    public Optional<JdbcUserAudit> findById(long id) throws SQLException {
        String sql = "SELECT * FROM ms_audit_user WHERE id = ?";
        JdbcUserAudit audit = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                audit = new JdbcUserAudit(rs.getLong("id"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getLong("user_id"),
                        rs.getString("action"));
            }
        }
        if (audit != null) {
            return Optional.of(audit);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<JdbcUserAudit> findAll() throws SQLException {
        String sql = "SELECT * FROM ms_audit_user";
        List<JdbcUserAudit> audits = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                JdbcUserAudit audit = new JdbcUserAudit(rs.getLong("id"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getLong("user_id"),
                        rs.getString("action"));
                audits.add(audit);
            }
        }
        return audits;
    }

    @Override
    public JdbcUserAudit save(JdbcUserAudit audit) throws SQLException {
        String sql = "INSERT INTO ms_audit_user (timestamp, auditing_entity_id, action) VALUES (?,?,?)";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(audit.getTimestamp()));
            stmt.setLong(2, audit.getAuditingEntityId());
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
