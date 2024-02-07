package com.dannyhromau.monitoring.meter.repository.impl.console;

import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MeterTypeRepositoryImpl implements MeterTypeRepository {
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static Map<Long, MeterType> meterStorage = new HashMap<>();
    private static long generatedId = 1;

    @Override
    public Optional<MeterType> findById(long id) {
        return Optional.of(meterStorage.get(id));
    }

    @Override
    public List<MeterType> findAll() {
        return meterStorage.values().stream().toList();
    }

    @Override
    public MeterType save(MeterType meterType) {
        meterType.setId(generatedId);
        meterStorage.put(meterType.getId(), meterType);
        generatedId++;
        return meterType;
    }

    @Override
    public long deleteById(long id) throws EntityNotFoundException {
        if (meterStorage.get(id) != null) {
            meterStorage.remove(id);
            return id;
        } else {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id));
        }
    }

    @Override
    public Optional<MeterType> findMeterTypeByType(String type) {
        return findAll()
                .stream()
                .filter(n -> n.getType().equals(type))
                .findFirst();
    }

    @Override
    public void deleteAll() {
        meterStorage.clear();
    }

    @Override
    public void addAll(List<MeterType> meterTypeList) {
        for (MeterType meterType : meterTypeList) {
            save(meterType);
        }
    }
}
