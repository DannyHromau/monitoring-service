package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;
import com.dannyhromau.monitoring.meter.service.MeterTypeService;

import java.util.List;
import java.util.Optional;

public class MeterTypeServiceImpl implements MeterTypeService {
    private final MeterTypeRepository meterTypeRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;

    public MeterTypeServiceImpl(MeterTypeRepository meterTypeRepository) {
        this.meterTypeRepository = meterTypeRepository;
    }

    @Override
    public MeterType getMeterById(long id) throws EntityNotFoundException {
        return meterTypeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<MeterType> getAll() {
        return meterTypeRepository.findAll();
    }

    @Override
    public MeterType add(MeterType meterType) throws DuplicateDataException {
        Optional<MeterType> meterOpt = meterTypeRepository.findMeterTypeByType(meterType.getType());
        if (meterOpt.isPresent()) {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        } else {
            meterType = meterTypeRepository.add(meterType);
        }
        return meterType;
    }

    @Override
    public long deleteMeter(long id) {
        meterTypeRepository.deleteById(id);
        return id;
    }

    @Override
    public MeterType getMeterByType(String type) throws EntityNotFoundException {
        return meterTypeRepository.findMeterTypeByType(type).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "type", type)));
    }
}
