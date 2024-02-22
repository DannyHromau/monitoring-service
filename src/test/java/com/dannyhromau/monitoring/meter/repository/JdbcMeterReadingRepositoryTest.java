package com.dannyhromau.monitoring.meter.repository;

import com.dannyhromau.monitoring.meter.model.MeterReading;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

@Testcontainers
@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of jdbc_meter_reading_repository")
class JdbcMeterReadingRepositoryTest {

    @Mock
    private Connection connection;

    private MeterReadingRepository meterReadingRepository;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @BeforeAll
    static void setUpContainer() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        String createUserSql = "CREATE TABLE IF NOT EXISTS ms_user (id BIGSERIAL PRIMARY KEY, name VARCHAR(255))";
        String insertUserSql = "INSERT INTO ms_user (id, name) VALUES (1, 'user')";
        String createTypeSql = "CREATE TABLE IF NOT EXISTS ms_meter_type (id BIGSERIAL PRIMARY KEY, type VARCHAR(255))";
        String insertMeterTypeSql = "INSERT INTO ms_meter_type (id, type) VALUES (1, 'Electricity')";
        String sql = "CREATE TABLE IF NOT EXISTS ms_meter_reading (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    date TIMESTAMP,\n" +
                "    value INT,\n" +
                "    user_id BIGINT,\n" +
                "    meter_type_id BIGINT,\n" +
                "    FOREIGN KEY (user_id) REFERENCES ms_user (id),\n" +
                "    FOREIGN KEY (meter_type_id) REFERENCES ms_meter_type (id));";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
               PreparedStatement crtTabUsrStmt = connection.prepareStatement(createUserSql);
               PreparedStatement crtTabTypeStmt = connection.prepareStatement(createTypeSql);
             PreparedStatement insertUserStmt = connection.prepareStatement(insertUserSql);
             PreparedStatement insertMeterTypeStmt = connection.prepareStatement(insertMeterTypeSql)) {
            crtTabUsrStmt.executeUpdate();
            insertUserStmt.executeUpdate();
            crtTabTypeStmt.executeUpdate();
            insertMeterTypeStmt.executeUpdate();
            stmt.executeUpdate();
        }

    }
    @AfterEach
    void clearTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String deleteTableSql = "DELETE FROM ms_meter_reading";
            String clearTableUserSql = "DELETE FROM ms_meter_type";
            String clearTableTypeSql = "DELETE FROM ms_user";
            statement.executeUpdate(deleteTableSql);
            statement.executeUpdate(clearTableTypeSql);
            statement.executeUpdate(clearTableUserSql);
        }
    }

    @Test
    @DisplayName("Save entity when not exists")
    void saveEntityWhenNotExists() throws SQLException {
        MeterReading meterReading = new MeterReading();
        int expectedValue = 1000;
        meterReading.setDate(LocalDateTime.now());
        meterReading.setValue(expectedValue);
        meterReading.setUserId(UUID.randomUUID());
        meterReading.setMeterTypeId(UUID.randomUUID());
        MeterReading result = meterReadingRepository.save(meterReading);
        int actualValue = result.getValue();
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("Find all entities when is not empty")
    void findAllWhenIsNotEmpty() throws SQLException {
        MeterReading meterReading = new MeterReading();
        meterReading.setDate(LocalDateTime.now());
        meterReading.setValue(100);
        meterReading.setUserId(UUID.randomUUID());
        meterReading.setMeterTypeId(UUID.randomUUID());
        meterReadingRepository.save(meterReading);
        Iterable<MeterReading> result = meterReadingRepository.findAll();
        List<MeterReading> actual = new ArrayList<>();
        result.forEach(actual::add);
        int expectedSize = 1;
        int actualSize = actual.size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("Find entity by user id when exists")
    void findByUserIdWhenExists() throws SQLException {
        MeterReading meterReading = new MeterReading();
        meterReading.setDate(LocalDateTime.now());
        meterReading.setValue(1000);
        meterReading.setUserId(UUID.randomUUID());
        meterReading.setMeterTypeId(UUID.randomUUID());
        meterReadingRepository.save(meterReading);
        List<MeterReading> expectedList = new LinkedList<>();
        expectedList.add(new MeterReading());
        List<MeterReading> result = meterReadingRepository.findByUserId(meterReading.getUserId());
        Assertions.assertEquals(expectedList.size(), result.size());
    }

    @Test
    @DisplayName("Find actual entity by user id and meter type id when exists")
    void findFirstByOrderByDateDescTest() throws SQLException{
        int expectedValue = 1000;
        MeterReading meterReading = new MeterReading();
        meterReading.setDate(LocalDateTime.now());
        meterReading.setValue(expectedValue);
        meterReading.setUserId(UUID.randomUUID());
        meterReading.setMeterTypeId(UUID.randomUUID());
        meterReadingRepository.save(meterReading);
        Optional<MeterReading> meterReadingOpt = meterReadingRepository
                .findFirstByOrderByDateDesc(meterReading.getUserId(), meterReading.getMeterTypeId());
        int actualValue = meterReadingOpt.get().getValue();
        Assertions.assertEquals(expectedValue, actualValue);
    }

}