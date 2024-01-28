package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.context.ApplicationContext;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class MeterTypesLoader {
    private static final String FILE_TYPES_PATH = "/meters.txt";

    public boolean load() {
        boolean result = false;
        try {
            MeterTypeRepository mr = ApplicationContext.getInstance().getContextHolder().getMeterTypeRepository();
            List<String> linesFromFile = Files.readAllLines(Path.of(FILE_TYPES_PATH));
            for (String meterTypeStr : linesFromFile) {
                Optional<MeterType> meterTypeOpt = mr.findMeterTypeByType(meterTypeStr);
                if (meterTypeOpt.isEmpty()) {
                    MeterType meterType = new MeterType();
                    meterType.setType(meterTypeStr);
                    mr.add(meterType);
                }
            }
            result = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
