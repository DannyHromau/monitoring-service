package com.dannyhromau.monitoring.meter.in.console.menu;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.context.ApplicationContext;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.core.util.MeterTypesLoader;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.in.console.ConsoleClient;

import java.util.List;
import java.util.Scanner;

public class AdminMenu implements Menu {
    private final User user;
    private final MeterReadingController mrController;
    private final ConsoleClient client;
    private final MeterTypeController meterTypeController;

    public AdminMenu(User user,
                     MeterReadingController mrController,
                     ConsoleClient client,
                     MeterTypeController meterTypeController) {
        this.user = user;
        this.mrController = mrController;
        this.client = client;
        this.meterTypeController = meterTypeController;
    }

    @Override
    public void launch() {
        Scanner in = new Scanner(System.in);
        System.out.println("Admin menu" + "\n" + "Press any key (1 - see all MR; 2 - update meter types;  0 - back)");
        String input = in.nextLine();
        switch (input) {
            case "1" -> getAllMeterReadings();
            case "2" -> updatesMeterTypes();
            case "0" -> client.start();
            default -> {
                System.out.println("Undefined command");
                launch();
            }
        }

    }

    private void updatesMeterTypes() {
        MeterTypesLoader loader = new MeterTypesLoader();
        boolean answer = loader.load();
        if (!answer) {
            System.out.println("failed");
            launch();
        } else {
            System.out.println("ok");
            launch();
        }
    }

    private void getAllMeterReadings() {
        ResponseEntity<List<MeterReading>> re = mrController.getAll();
        StringBuilder builder = new StringBuilder();
        if (re.getBody() == null) {
            System.out.println(re.getSystemMessage());
            launch();
        } else if (!re.getBody().isEmpty()) {
            for (MeterReading meterReading : re.getBody()) {
                meterReading.setMeterType(meterTypeController.getMeterById(meterReading.getMeterTypeId()).getBody());
                builder.append("user id = ")
                        .append(meterReading.getUserId()).append(": ")
                        .append(meterReading).append("\n");
            }
            System.out.println(builder.toString().trim());
            launch();
        } else {
            System.out.println("empty history \n" + re.getSystemMessage());
            launch();
        }
    }
}
