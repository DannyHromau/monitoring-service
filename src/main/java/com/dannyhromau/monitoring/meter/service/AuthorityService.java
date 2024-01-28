package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;

import java.util.List;

public interface AuthorityService {
    Authority getAuthorityById(long id) throws EntityNotFoundException;

    List<Authority> getAll();

    Authority add(Authority authority) throws DuplicateDataException;

    long deleteAuthority(long id);

    Authority getAuthorityByName(String name) throws EntityNotFoundException;
}
