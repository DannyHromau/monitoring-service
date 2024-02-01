package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.repository.impl.jdbc.JdbcAuthorityRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of jdbc_authority_repository")
public class JdbcAuthorityRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("postgres");
    @Mock
    private JdbcUtil jdbcUtil;
    private AuthorityRepository authorityRepository;
    private Connection connection;
    private static String propertyFilePath = "db/jdbc.properties";


    @BeforeAll
    static void setUpContainer(){
        postgreSQLContainer.start();
    }

    @AfterAll
    static void stopContainer(){
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        when(jdbcUtil.getConnection())
                .thenReturn(DriverManager
                        .getConnection(
                                postgreSQLContainer.getJdbcUrl(),
                                postgreSQLContainer.getUsername(),
                                postgreSQLContainer.getPassword()));
        authorityRepository = new JdbcAuthorityRepository(jdbcUtil);
        connection = jdbcUtil.getConnection();
        try (Statement statement = connection.createStatement()) {
            String createTableSql = "CREATE TABLE IF NOT EXISTS ms_authority (id BIGSERIAL, name VARCHAR(255))";
            statement.executeUpdate(createTableSql);
        }
        try (Statement statement = connection.createStatement()) {
            String deleteTableSql = "DELETE FROM ms_authority";
            statement.executeUpdate(deleteTableSql);
        }
    }

    @Test
    @DisplayName("Save entity when not exists")
    void saveEntityWhenNotExists() throws SQLException {
        String expectedName = "admin";
        Authority authority = new Authority();
        authority.setName(expectedName);
        String actualName = authorityRepository.save(authority).getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("Find entity by id when exists")
    void findEntityByIdWhenExists() throws SQLException {
        String expectedName = "admin";
        Authority authority = new Authority();
        authority.setName(expectedName);
        authorityRepository.save(authority);
        when(jdbcUtil.getConnection())
                .thenReturn(DriverManager
                        .getConnection(
                                postgreSQLContainer.getJdbcUrl(),
                                postgreSQLContainer.getUsername(),
                                postgreSQLContainer.getPassword()));
        Optional<Authority> optionalAuthority = authorityRepository.findById(authority.getId());
        assertTrue(optionalAuthority.isPresent());
        String actualName = optionalAuthority.get().getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("Find entity by name when exists")
    void findEntityByNameWhenExists() throws SQLException {
        String expectedName = "admin";
        Authority authority = new Authority();
        authority.setName(expectedName);
        authorityRepository.save(authority);
        when(jdbcUtil.getConnection())
                .thenReturn(DriverManager
                        .getConnection(
                                postgreSQLContainer.getJdbcUrl(),
                                postgreSQLContainer.getUsername(),
                                postgreSQLContainer.getPassword()));
        Optional<Authority> optionalAuthority = authorityRepository.findByName(authority.getName());
        assertTrue(optionalAuthority.isPresent());
        String actualName = optionalAuthority.get().getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    @DisplayName("Find all entities when is not empty")
    void findAllEntities() throws SQLException {
        Authority authority1 = new Authority();
        authority1.setName("admin");
        Authority authority2 = new Authority();
        authority2.setName("user");
        authorityRepository.save(authority1);
        when(jdbcUtil.getConnection())
                .thenReturn(DriverManager
                        .getConnection(
                                postgreSQLContainer.getJdbcUrl(),
                                postgreSQLContainer.getUsername(),
                                postgreSQLContainer.getPassword()));
        authorityRepository.save(authority2);
        when(jdbcUtil.getConnection())
                .thenReturn(DriverManager
                        .getConnection(
                                postgreSQLContainer.getJdbcUrl(),
                                postgreSQLContainer.getUsername(),
                                postgreSQLContainer.getPassword()));
        List<Authority> authorities = authorityRepository.findAll();
        assertEquals(2, authorities.size());
    }
}
