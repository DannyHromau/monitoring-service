package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AuthorityRepository {

    Optional<Authority> findById(long id) throws SQLException;

    Optional<Authority> findByName(String name) throws SQLException;

    Authority save(Authority authority) throws SQLException;

    List<Authority> findAll() throws SQLException;

    long deleteById(long id) throws SQLException, EntityNotFoundException;

    void deleteAll();

    void addAll(List<Authority> authorities) throws SQLException;
}
