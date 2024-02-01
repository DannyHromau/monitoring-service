package com.dannyhromau.monitoring.meter.controller.impl;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.service.MeterTypeService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

//TODO: add facade layer
@RequiredArgsConstructor
public class MeterTypeControllerImpl implements MeterTypeController {
    private final MeterTypeService meterTypeService;
    private static final String STATUS_OK = "ok";

    @Override
    public ResponseEntity<MeterType> getMeterById(long id) {
        ResponseEntity<MeterType> re = new ResponseEntity<>();
        try {
            re.setBody(meterTypeService.getMeterById(id));
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException | SQLException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }

    @Override
    public ResponseEntity<List<MeterType>> getAll() {
        ResponseEntity<List<MeterType>> re = new ResponseEntity<>();
        try {
            re.setBody(meterTypeService.getAll());
            re.setSystemMessage(STATUS_OK);
        } catch (SQLException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterType> add(MeterType meterType) {
        ResponseEntity<MeterType> re = new ResponseEntity<>();
        try {
            meterTypeService.add(meterType);
        } catch (DuplicateDataException | SQLException e) {
            re.setBody(meterType);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }

    @Override
    public ResponseEntity<MeterType> getMeterByType(String type) {
        ResponseEntity<MeterType> re = new ResponseEntity<>();
        try {
            re.setBody(meterTypeService.getMeterByType(type));
            re.setSystemMessage(STATUS_OK);
        } catch (EntityNotFoundException | SQLException e) {
            re.setBody(null);
            re.setSystemMessage(e.getMessage());
        }
        return re;
    }
}
