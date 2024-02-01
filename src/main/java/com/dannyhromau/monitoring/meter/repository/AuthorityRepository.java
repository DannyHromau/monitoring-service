package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository {

    Optional<Authority> findById(long id);

    Optional<Authority> findByName(String name);

    Authority add(Authority authority);

    List<Authority> findAll();

    void deleteById(long id);

    void deleteAll();

    void addAll(List<Authority> authorities);
}
