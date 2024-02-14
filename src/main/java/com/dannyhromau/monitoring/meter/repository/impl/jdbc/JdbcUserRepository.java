package com.dannyhromau.monitoring.meter.repository.impl.jdbc;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@AspectLogging
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final JdbcUtil jdbcUtil;

    //implemented lazy loading
    @Override
    public Optional<User> findById(long id) throws SQLException {
        String sql = "SELECT * FROM ms_user WHERE id = ?";
        User user = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Authority> authorities = new ArrayList<>();
            while (rs.next()) {
                user = new User(rs.getLong("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getBoolean("is_deleted"),
                        new ArrayList<>());
            }
            if (user != null) {
                user.setAuthorities(authorities);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        }
    }

    //implemented lazy loading
    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM ms_user";
        List<User> users = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getLong("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getBoolean("is_deleted"),
                        new ArrayList<>());
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public User save(User user) throws SQLException {
        String sql = "INSERT INTO ms_user (login, password, is_deleted) VALUES (?, ?, ?)";
        String userAuthoritySql = "INSERT INTO ms_user_authority (user_id, authority_id) VALUES (?, ?)";
        Connection connection = jdbcUtil.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement userAuthorityStmt = connection.prepareStatement(userAuthoritySql)) {
            connection.setAutoCommit(false);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setBoolean(3, user.isDeleted());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId((generatedKeys.getLong(1)));
            }
            for (Authority authority : user.getAuthorities()) {
                userAuthorityStmt.setLong(1, user.getId());
                userAuthorityStmt.setLong(2, authority.getId());
                userAuthorityStmt.executeUpdate();
            }
            connection.commit();
            return user;
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e.getMessage(), e);
        } finally {
            connection.close();
        }
    }

    //TODO: implement deleting by id
    @Override
    public void deleteById(long id) {

    }

    //implemented eager loading for security
    @Override
    public Optional<User> findUserByLogin(String login) throws SQLException {
        String sql = "SELECT u.id, u.login, u.password, u.is_deleted, a.id, a.name " +
                "FROM ms_user u " +
                "JOIN ms_user_authority ua ON u.id = ua.user_id " +
                "JOIN ms_authority a ON ua.authority_id = a.id " +
                "WHERE u.login = ?";
        User user = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            List<Authority> authorities = new ArrayList<>();
            while (rs.next()) {
                user = new User(rs.getLong("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getBoolean("is_deleted"),
                        new ArrayList<>());
                Authority authority = new Authority();
                authority.setId(rs.getLong("id"));
                authority.setName(rs.getString("name"));
                authorities.add(authority);
            }
            if (user != null) {
                user.setAuthorities(authorities);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        }
    }

    //TODO: implement deleting all (soft deleting)
    @Override
    public void deleteAll() {

    }
}
