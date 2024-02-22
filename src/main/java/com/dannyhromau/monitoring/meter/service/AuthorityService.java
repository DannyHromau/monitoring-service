package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;
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
