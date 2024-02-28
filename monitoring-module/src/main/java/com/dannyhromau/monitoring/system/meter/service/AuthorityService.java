package com.dannyhromau.monitoring.system.meter.service;

import com.dannyhromau.monitoring.system.meter.model.Authority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AuthorityService {
    Authority getById(UUID id);

    List<Authority> getAll();

    Authority add(Authority authority);

    UUID deleteById(UUID id);

    Authority getAuthorityByName(String name);
}
