package com.dannyhromau.monitoring.meter.service.impl;

import com.dannyhromau.monitoring.meter.annotation.AspectLogging;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;
import com.dannyhromau.monitoring.meter.service.MeterTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@AspectLogging
@RequiredArgsConstructor
public class MeterTypeServiceImpl implements MeterTypeService {
    private final MeterTypeRepository meterTypeRepository;
    private static final String ENTITY_NOT_FOUND_MESSAGE = ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label;
    private static final String DUPLICATE_DATA_MESSAGE = ErrorMessages.DUPLICATED_DATA_MESSAGE.label;
    @Value("${meter.system.path}")
    private String fileTypesPath;

    @Override
    public MeterType getMeterById(UUID id) {
        return meterTypeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "id", id)));
    }

    @Override
    public List<MeterType> getAll() {
        return meterTypeRepository.findAll();
    }

    @Override
    public MeterType add(MeterType meterType) {
        Optional<MeterType> meterOpt = meterTypeRepository.findMeterTypeByType(meterType.getType());
        if (meterOpt.isPresent()) {
            throw new DuplicateDataException(DUPLICATE_DATA_MESSAGE);
        } else {
            meterType = meterTypeRepository.save(meterType);
        }
        return meterType;
    }

    @Override
    public UUID deleteMeter(UUID id) {
        meterTypeRepository.deleteById(id);
        return id;
    }

    @Override
    public MeterType getMeterByType(String type) {
        return meterTypeRepository.findMeterTypeByType(type).orElseThrow(
                () -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MESSAGE, "type", type)));
    }

    public boolean loadMeterTypesFromFile() {
        boolean result = false;
        if (fileTypesPath.isBlank() || fileTypesPath == null) {
            fileTypesPath = "meters.txt";
        }
        try {
            List<String> linesFromFile = Files.readAllLines(Path.of(fileTypesPath));
            List<MeterType> meterTypes = new ArrayList<>();
            for (String meterTypeStr : linesFromFile) {
                MeterType meterType = new MeterType();
                meterType.setType(meterTypeStr);
                meterTypes.add(meterType);
            }
            meterTypeRepository.saveAll(meterTypes);
            result = true;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
