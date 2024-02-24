package com.dannyhromau.monitoring.system.meter.repository;

import com.dannyhromau.monitoring.system.meter.model.Authority;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends ListCrudRepository<Authority, UUID> {
    Optional<Authority> findByName(@Param("name") String name);
}
