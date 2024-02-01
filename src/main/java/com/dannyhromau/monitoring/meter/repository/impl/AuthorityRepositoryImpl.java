package com.dannyhromau.monitoring.meter.repository.impl;

import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthorityRepositoryImpl implements AuthorityRepository {
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
    public Authority add(Authority authority) {
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
    public void deleteById(long id) {
        if (authStorage.get(id) != null) {
            authStorage.remove(id);
        }
    }

    @Override
    public void deleteAll() {
        authStorage.clear();
    }

    @Override
    public void addAll(List<Authority> authorities) {
        for (Authority authority : authorities) {
            add(authority);
        }
    }

    public long resetIdGenerator() {
        return generatedId = 1;
    }
}
