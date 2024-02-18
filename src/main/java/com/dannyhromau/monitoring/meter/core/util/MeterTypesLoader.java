package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.controller.impl.AuthControllerImpl;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO: implement admin console to load meter type in runtime
@Component
@RequiredArgsConstructor
public class MeterTypesLoader {
    private static String fileTypesPath = System.getenv("METERS_FILE_PATH");
    private static final Logger logger = LogManager.getLogger(AuthControllerImpl.class);
    private final MeterTypeRepository meterTypeRepository;

    public boolean load() {
        boolean result = false;
        if (fileTypesPath == null) {
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
            meterTypeRepository.addAll(meterTypes);
            result = true;
        } catch (IOException | SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
