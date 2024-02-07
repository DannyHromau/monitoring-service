package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.context.ApplicationContext;
import com.dannyhromau.monitoring.meter.controller.impl.AuthControllerImpl;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeterTypesLoader {
    private static String fileTypesPath= System.getenv("METERS_FILE_PATH");
    private static final Logger logger = LogManager.getLogger(AuthControllerImpl.class);

    public boolean load() {
        boolean result = false;
        if (fileTypesPath == null){
            fileTypesPath = "/meters.txt";
        }
        try {
            MeterTypeRepository mr = ApplicationContext.getInstance().getContextHolder().getMeterTypeRepository();
            List<String> linesFromFile = Files.readAllLines(Path.of(fileTypesPath));
            List<MeterType> meterTypes = new ArrayList<>();
            for (String meterTypeStr : linesFromFile) {
                MeterType meterType = new MeterType();
                meterType.setType(meterTypeStr);
                meterTypes.add(meterType);
            }
            mr.addAll(meterTypes);
            result = true;
        } catch (IOException | SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
