package com.dannyhromau.monitoring.meter.service;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;
import com.dannyhromau.monitoring.meter.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
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
    void addAuthorityWhenExists() throws SQLException {
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
    void getAuthorityByIdWhenNotExists() throws SQLException {
        when(authorityRepository.findById(1))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> authorityService.getById(1))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", 1);
    }

    @Test
    @DisplayName("get authority by login when not exists")
    void getAuthorityByLoginWhenNotExists() throws SQLException {
        String name = "admin";
        when(authorityRepository.findByName(name))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> authorityService.getAuthorityByName(name))
                .withMessage(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "name", name);
    }
}
