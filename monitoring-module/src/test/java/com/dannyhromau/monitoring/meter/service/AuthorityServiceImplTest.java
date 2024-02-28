package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.system.meter.Application;
import com.dannyhromau.monitoring.system.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.system.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.system.meter.model.Authority;
import com.dannyhromau.monitoring.system.meter.repository.AuthorityRepository;
import com.dannyhromau.monitoring.system.meter.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
@DisplayName("Testing of authority_service")
public class AuthorityServiceImplTest {
    @Mock
    private AuthorityRepository authorityRepository;

    private AuthorityServiceImpl authorityService;

    @BeforeEach
    void setUp() {
        authorityService = new AuthorityServiceImpl(authorityRepository);
    }

    @Test
    @DisplayName("add authority when exists")
    void addAuthorityWhenExists() {
        Authority authority = new Authority();
        authority.setName("admin");
        when(authorityRepository.findByName(authority.getName()))
                .thenReturn(Optional.of(authority));
        assertThatExceptionOfType(DuplicateDataException.class)
                .isThrownBy(() -> authorityService.add(authority))
                .withMessage(ErrorMessages.DUPLICATED_DATA_MESSAGE.label);
    }

    @Test
    @DisplayName("get authority by id when not exists")
    void getAuthorityByIdWhenNotExists() {
        UUID id = UUID.randomUUID();
        when(authorityRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> authorityService.getById(id))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", id.toString());
    }

    @Test
    @DisplayName("get authority by login when not exists")
    void getAuthorityByLoginWhenNotExists() {
        String name = "admin";
        when(authorityRepository.findByName(name))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> authorityService.getAuthorityByName(name))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "name", name);
    }
}
