package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;
import com.dannyhromau.monitoring.meter.service.AuthorityService;

import java.util.List;
import java.util.Optional;

public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getAuthorityById(long id) throws EntityNotFoundException {
        return authorityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority add(Authority authority) throws DuplicateDataException {
        Optional<Authority> authorityOpt = authorityRepository.findByName(authority.getName());
        if (authorityOpt.isPresent()) {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        } else {
            authority = authorityRepository.add(authority);
        }
        return authority;
    }

    @Override
    public long deleteAuthority(long id) {
        authorityRepository.deleteById(id);
        return id;
    }

    @Override
    public Authority getAuthorityByName(String name) throws EntityNotFoundException {
        return authorityRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "name", name)));
    }
}
