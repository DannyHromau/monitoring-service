package com.dannyhromau.monitoring.meter.repository.impl.jdbc;

import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcAuthorityRepository implements AuthorityRepository {

    private final JdbcUtil jdbcUtil;

    @Override
    public Optional<Authority> findById(long id) throws SQLException {
        String sql = "SELECT * FROM ms_authority WHERE id = ?";
        Authority authority = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    authority = new Authority(rs.getLong("id"),
                            rs.getString("name"));
                }
            }
        }
        if (authority != null) {
            return Optional.of(authority);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Authority> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM ms_authority WHERE name = ?";
        Authority authority = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    authority = new Authority(rs.getLong("id"),
                            rs.getString("name"));
                }
            }
        }
        if (authority != null) {
            return Optional.of(authority);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Authority save(Authority authority) throws SQLException {
        String sql = "INSERT INTO ms_authority (name) VALUES (?)";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, authority.getName());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    authority.setId((generatedKeys.getLong(1)));
                }
            }
            return authority;
        }
    }

    @Override
    public List<Authority> findAll() throws SQLException {
        String sql = "SELECT * FROM ms_authority";
        List<Authority> authorities = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Authority authority = new Authority(rs.getLong("id"),
                            rs.getString("name"));
                    authorities.add(authority);
                }
            }
        }
        return authorities;
    }

    @Override
    public long deleteById(long id) throws SQLException {
        String sql = "DELETE FROM ms_authority WHERE id = ?";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
        return id;
    }

    //TODO: implement deleting all entities
    @Override
    public void deleteAll() {

    }

    @Override
    public void addAll(List<Authority> authorities) throws SQLException {
        for (Authority authority : authorities) {
            if (findByName(authority.getName()).isEmpty()) {
                save(authority);
            }
        }
    }
}
