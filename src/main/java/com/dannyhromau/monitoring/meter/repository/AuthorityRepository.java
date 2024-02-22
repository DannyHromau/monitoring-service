package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.Authority;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends ListCrudRepository<Authority, UUID> {
    Optional<Authority> findByName(String name);
}
