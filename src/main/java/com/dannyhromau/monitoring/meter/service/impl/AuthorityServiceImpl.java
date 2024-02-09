package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;
import com.dannyhromau.monitoring.meter.service.AuthorityService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AspectLogging
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;


    @Override
    public Authority getById(long id) throws EntityNotFoundException, SQLException {
        return authorityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<Authority> getAll() throws SQLException {
        return authorityRepository.findAll();
    }

    @Override
    public Authority add(Authority authority) throws DuplicateDataException, SQLException {
        Optional<Authority> authorityOpt = authorityRepository.findByName(authority.getName());
        if (authorityOpt.isPresent()) {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        } else {
            authority = authorityRepository.save(authority);
        }
        return authority;
    }

    @Override
    public long deleteById(long id) throws SQLException, EntityNotFoundException {
        authorityRepository.deleteById(id);
        return id;
    }

    @Override
    public Authority getAuthorityByName(String name) throws EntityNotFoundException, SQLException {
        return authorityRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "name", name)));
    }
}
