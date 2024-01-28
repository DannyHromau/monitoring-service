package com.dannyhromau.monitoring.meter;

import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.impl.AuthorityRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing of authority_repository")
public class AuthorityRepositoryImplTest {

    private AuthorityRepositoryImpl authorityRepository = new AuthorityRepositoryImpl();
    private Authority authority = new Authority();

    @BeforeEach
    void setup(){
        authorityRepository.deleteAll();
        authorityRepository.resetIdGenerator();
        authority.setName("admin");
    }

    @Test
    @DisplayName("find authority by id when empty repository")
    void getAuthorityByIdWhenNotExists(){
        authorityRepository.add(authority);
        int expectedSize = 1;
        int actualSize = authorityRepository.findAll().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }
    @Test
    @DisplayName("check incrementing id when add authority")
    void incrementIdWhenAddAuthority(){
        authorityRepository.add(authority);
        Authority authorityNext = new Authority();
        authorityRepository.add(authorityNext);
        long expectedId = 2;
        long actualId = authorityRepository.findAll().get(1).getId();
        Assertions.assertEquals(expectedId, actualId);
    }

    @Test
    @DisplayName("find authority by name when exists")
    void findAuthorityByNameWhenExists(){
        Authority expectedAuthority = authority;
        authorityRepository.add(authority);
        Authority actualAuthority = authorityRepository.findByName(authority.getName()).get();
        Assertions.assertEquals(expectedAuthority, actualAuthority);
    }
    @Test
    @DisplayName("delete authority when exists")
    void deleteAuthorityWhenExists(){
        authorityRepository.add(authority);
        authorityRepository.add(new Authority());
        long id = authorityRepository.findByName(authority.getName()).get().getId();
        authorityRepository.deleteById(id);
        int expectedSize = 1;
        int actualSize = authorityRepository.findAll().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }
}
