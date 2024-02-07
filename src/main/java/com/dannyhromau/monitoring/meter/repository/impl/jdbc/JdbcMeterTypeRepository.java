package com.dannyhromau.monitoring.meter.repository.impl.jdbc;

import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcMeterTypeRepository implements MeterTypeRepository {

    private final JdbcUtil jdbcUtil;

    @Override
    public Optional<MeterType> findById(long id) throws SQLException {
        String sql = "SELECT * FROM ms_meter_type WHERE id = ?";
        MeterType meterType = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                meterType = new MeterType(rs.getLong("id"),
                        rs.getString("type"));
            }
        }
        if (meterType != null) {
            return Optional.of(meterType);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MeterType> findAll() throws SQLException {
        String sql = "SELECT * FROM ms_meter_type";
        List<MeterType> meterTypes = new LinkedList<>();
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MeterType meterType = new MeterType(rs.getLong("id"),
                        rs.getString("type"));
                meterTypes.add(meterType);
            }
        }
        return meterTypes;
    }

    @Override
    public MeterType save(MeterType meterType) throws SQLException {
        String sql = "INSERT INTO ms_meter_type (type) VALUES (?)";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, meterType.getType());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                meterType.setId((generatedKeys.getLong(1)));
            }
            return meterType;
        }
    }

    @Override
    public long deleteById(long id) throws SQLException {
        String sql = "DELETE FROM ms_meter_type WHERE id = ?";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
        return id;
    }

    @Override
    public Optional<MeterType> findMeterTypeByType(String type) throws SQLException {
        String sql = "SELECT * FROM ms_meter_type WHERE type = ?";
        MeterType meterType = null;
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                meterType = new MeterType(rs.getLong("id"),
                        rs.getString("type"));
            }
        }
        if (meterType != null) {
            return Optional.of(meterType);
        } else {
            return Optional.empty();
        }
    }

    //TODO: implement deleting all entities
    @Override
    public void deleteAll() {

    }

    @Override
    public void addAll(List<MeterType> meterTypeList) throws SQLException {
        for (MeterType meterType : meterTypeList){
            if (findMeterTypeByType(meterType.getType()).isEmpty()){
                save(meterType);
            }
        }
    }
}
