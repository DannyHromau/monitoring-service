package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findById(long id) throws SQLException;

    List<User> findAll() throws SQLException;

    User save(User user) throws SQLException;

    void deleteById(long id);

    Optional<User> findUserByLogin(String login) throws SQLException;

    void deleteAll();
}
