package com.dannyhromau.monitoring.meter.repository.impl.console;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthorityRepositoryImpl implements AuthorityRepository {
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static Map<Long, Authority> authStorage = new HashMap<>();
    private static long generatedId = 1;

    @Override
    public Optional<Authority> findById(long id) {
        return Optional.of(authStorage.get(id));
    }

    @Override
    public Optional<Authority> findByName(String name) {
        return findAll()
                .stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();
    }

    @Override
    public Authority save(Authority authority) {
        authority.setId(generatedId);
        authStorage.put(authority.getId(), authority);
        generatedId++;
        return authority;
    }

    @Override
    public List<Authority> findAll() {
        return authStorage.values().stream().toList();
    }

    @Override
    public long deleteById(long id) throws EntityNotFoundException {
        if (authStorage.get(id) != null) {
            authStorage.remove(id);
            return id;
        }
        else {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id));
        }
    }

    @Override
    public void deleteAll() {
        authStorage.clear();
    }

    @Override
    public void addAll(List<Authority> authorities) {
        for (Authority authority : authorities) {
            save(authority);
        }
    }

    public long resetIdGenerator() {
        return generatedId = 1;
    }
}
