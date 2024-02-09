package com.dannyhromau.monitoring.meter.repository.impl.jdbc;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@AspectLogging
@RequiredArgsConstructor
public class JdbcMeterReadingRepository implements MeterReadingRepository {
    private final JdbcUtil jdbcUtil;

    @Override
    public MeterReading save(MeterReading mr) throws SQLException {
        String sql = "INSERT INTO ms_meter_reading (date, value, user_id, meter_type_id) VALUES (?,?,?,?)";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(mr.getDate()));
            stmt.setInt(2, mr.getValue());
            stmt.setLong(3, mr.getUserId());
            stmt.setLong(4, mr.getMeterTypeId());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                mr.setId((generatedKeys.getLong(1)));
            }
            return mr;
        }
    }

    @Override
    public List<MeterReading> findAll() throws SQLException {
        String sql = "SELECT * FROM ms_meter_reading";
        List<MeterReading> mrList = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MeterReading meterReading = new MeterReading(rs.getLong("id"),
                        new MeterType(),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getInt("value"),
                        rs.getLong("user_id"),
                        rs.getLong("meter_type_id"));
                mrList.add(meterReading);
            }
        }
        return mrList;
    }

    @Override
    public List<MeterReading> findByUserId(long userId) throws SQLException {
        String sql = "SELECT * FROM ms_meter_reading WHERE user_id = ?";
        List<MeterReading> mrList = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MeterReading meterReading = new MeterReading(rs.getLong("id"),
                            new MeterType(),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getInt("value"),
                            rs.getLong("user_id"),
                            rs.getLong("meter_type_id"));
                    mrList.add(meterReading);
                }
            }
        }
        return mrList;
    }


    @Override
    public Optional<MeterReading> findById(long id) throws SQLException {
        String sql = "SELECT * FROM ms_meter_reading WHERE id = ?";
        MeterReading meterReading = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    meterReading = new MeterReading(rs.getLong("id"),
                            new MeterType(),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getInt("value"),
                            rs.getLong("user_id"),
                            rs.getLong("meter_type_id"));
                }
            }
        }
        if (meterReading != null) {
            return Optional.of(meterReading);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MeterReading> findByUserIdAndMeterType(long userId, long mrTypeId) throws SQLException {
        String sql = "SELECT * FROM ms_meter_reading WHERE user_id = ? AND meter_type_id = ?";
        List<MeterReading> mrList = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, mrTypeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MeterReading meterReading = new MeterReading(rs.getLong("id"),
                            new MeterType(),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getInt("value"),
                            rs.getLong("user_id"),
                            rs.getLong("meter_type_id"));
                    mrList.add(meterReading);
                }
            }
        }
        return mrList;
    }

    @Override
    public Optional<MeterReading> findByUserIdAndDateAndMeterType(long userId, LocalDate date, long meterTypeId) throws SQLException {
        String sql = "SELECT * FROM ms_meter_reading WHERE user_id = ? AND date= ? AND meter_type_id = ?";
        MeterReading meterReading = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setLong(3, meterTypeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    meterReading = new MeterReading(rs.getLong("id"),
                            new MeterType(),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getInt("value"),
                            rs.getLong("user_id"),
                            rs.getLong("meter_type_id"));
                }
            }
        }
        if (meterReading != null) {
            return Optional.of(meterReading);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MeterReading> findByUserIdAndMonthAndMeterType(long userId, YearMonth yearMonth, long meterTypeId)
            throws SQLException {
        String sql =  "SELECT * " +
                "FROM ms_meter_reading " +
                "WHERE user_id = ? " +
                "AND extract(year from date) = ? " +
                "AND extract(month from date) = ? " +
                "AND meter_type_id = ?";
        MeterReading meterReading = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setInt(2, yearMonth.getYear());
            stmt.setInt(3, yearMonth.getMonthValue());
            stmt.setLong(4, meterTypeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    meterReading = new MeterReading(rs.getLong("id"),
                            new MeterType(),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getInt("value"),
                            rs.getLong("user_id"),
                            rs.getLong("meter_type_id"));
                }
            }
        }
        if (meterReading != null) {
            return Optional.of(meterReading);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<MeterReading> findFirstByOrderByDateDesc(long userId, long meterTypeId) throws SQLException {
        String sql = "SELECT * " +
                "FROM ms_meter_reading " +
                "WHERE user_id = ? " +
                "AND meter_type_id = ? " +
                "ORDER BY date DESC LIMIT 1";
        MeterReading meterReading = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, meterTypeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    meterReading = new MeterReading(rs.getLong("id"),
                            new MeterType(),
                            rs.getTimestamp("date").toLocalDateTime(),
                            rs.getInt("value"),
                            rs.getLong("user_id"),
                            rs.getLong("meter_type_id"));
                }
            }
        }
        if (meterReading != null) {
            return Optional.of(meterReading);
        } else {
            return Optional.empty();
        }
    }
}
