package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;

import java.sql.SQLException;
import java.util.List;

public interface AuthorityService {
    Authority getById(long id) throws EntityNotFoundException, SQLException;

    List<Authority> getAll() throws SQLException;

    Authority add(Authority authority) throws DuplicateDataException, SQLException;

    long deleteById(long id) throws SQLException, EntityNotFoundException;

    Authority getAuthorityByName(String name) throws EntityNotFoundException, SQLException;
}
