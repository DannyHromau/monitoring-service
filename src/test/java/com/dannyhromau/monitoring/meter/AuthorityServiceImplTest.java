package com.dannyhromau.monitoring.meter;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;
import com.dannyhromau.monitoring.meter.service.AuthorityService;
import com.dannyhromau.monitoring.meter.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Testing of authority_service")
public class AuthorityServiceImplTest {
    private AuthorityRepository authorityRepository = Mockito.mock(AuthorityRepository.class);
    private AuthorityService authorityService = new AuthorityServiceImpl(authorityRepository);

    @Test
    @DisplayName("add authority when exists")
    void addAuthorityWhenExists() {
        Authority authority = new Authority();
        authority.setName("admin");
        when(authorityRepository.findByName(authority.getName()))
                .thenReturn(Optional.of(authority));
        Exception exception = assertThrows(DuplicateDataException.class, () ->
                authorityService.add(authority));
        String expectedMessage = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("get authority by id when not exists")
    void getAuthorityByIdWhenNotExists() {
        when(authorityRepository.findById(1))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                authorityService.getAuthorityById(1));
        String expectedMessage = String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "id", 1);
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("get authority by login when not exists")
    void getAuthorityByLoginWhenNotExists() {
        String name = "admin";
        when(authorityRepository.findByName(name))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                authorityService.getAuthorityByName(name));
        String expectedMessage = String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, "name", name);
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
