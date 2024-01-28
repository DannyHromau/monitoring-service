package com.dannyhromau.monitoring.meter.in.console.menu;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.model.MeterReading;
import com.dannyhromau.monitoring.meter.model.MeterType;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.in.console.ConsoleClient;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class UserMenu implements Menu {
    private final User user;
    private final MeterReadingController mrController;
    private final ConsoleClient client;
    private final MeterTypeController meterTypeController;

    public UserMenu(User user,
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
        System.out.println("Meter reading menu" + "\n" + "Choose MR type");
        System.out.println(getMeterReadingsText());
        String input = in.nextLine();
        MeterType meterType = new MeterType();
        List<MeterType> meterTypeList = meterTypeController.getAll().getBody();
        for (int i = 1; i <= meterTypeList.size(); i++) {
            if (input.equals(String.valueOf(i))) {
                meterType = meterTypeController.getAll().getBody().get(i - 1);
                break;
            }
        }
        if (input.equals("0")) {
            client.start();
        } else if (meterType.getType() != null) {
            chooseMeterType(meterType, user.getId());
        } else {
            System.out.println("Undefined command\n");
            launch();
        }
    }

    private void chooseMeterType(MeterType mrType, long userId) {
        Scanner in = new Scanner(System.in);
        System.out.println("Meter reading menu" + " (" + mrType.getType() + ")" + "\n" + "Press any key");
        System.out.println("1 - add new MR; 2 - get actual MR; 3 - all history; 4 - MR by month; 0 - back");
        String input = in.nextLine();
        switch (input) {
            case "1" -> addMeterReading(mrType, userId);
            case "2" -> getActualMeterReading(mrType, userId);
            case "3" -> getHistory(userId, mrType);
            case "4" -> getByMonth(mrType, userId);
            case "0" -> launch();
            default -> {
                System.out.println("Undefined command");
                chooseMeterType(mrType, userId);
            }
        }
    }

    private void getByMonth(MeterType meterType, long userId) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the data or enter the back to return. (format example: 2024-01)");
        String input = in.nextLine();
        if (input.equals("back")) {
            chooseMeterType(meterType, userId);
        }
        ResponseEntity<MeterReading> re = new ResponseEntity<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        try {
            YearMonth ym = YearMonth.parse(input, formatter);
            re = mrController.getMeterReadingByMonthAndMeterType(userId, ym, meterType);
        } catch (DateTimeParseException e) {
            System.out.println(ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label);
            getByMonth(meterType, userId);
        }
        if (re.getSystemMessage().equals("ok")) {
            System.out.println(re.getBody());
            chooseMeterType(meterType, userId);
        } else {
            System.out.println(re.getSystemMessage());
            chooseMeterType(meterType, userId);
        }

    }

    private void getHistory(long userId, MeterType meterType) {
        ResponseEntity<List<MeterReading>> re = mrController.getByUserIdAndMeterType(userId, meterType);
        StringBuilder builder = new StringBuilder();
        for (MeterReading meterReading : re.getBody()) {
            builder.append(meterReading).append("\n");
        }
        String result = builder.isEmpty() ? "empty history" : builder.toString().trim();
        System.out.println(result);
        chooseMeterType(meterType, userId);
    }

    private void getActualMeterReading(MeterType meterType, long userId) {
        ResponseEntity<MeterReading> re = mrController.getActualMeterReading(userId, meterType);
        if (re.getSystemMessage().equals("ok")) {
            System.out.println(re.getBody());
            chooseMeterType(meterType, userId);
        } else {
            System.out.println(re.getSystemMessage());
            chooseMeterType(meterType, userId);
        }
    }

    private void addMeterReading(MeterType meterType, long userId) {
        MeterReading mr = new MeterReading();
        mr.setMeterReadingType(meterType);
        mr.setUserId(userId);
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the meter reading or enter back to return");
        String input = in.nextLine();
        if (input.equals("back")) {
            chooseMeterType(meterType, userId);
        }
        try {
            int value = Integer.parseInt(input);
            mr.setValue(value);
            in = new Scanner(System.in);
            System.out.println("1 - save; 0 - back");
            input = in.nextLine();
            mr.setDate(LocalDateTime.now());
            switch (input) {
                case "1" -> System.out.println(mrController.add(mr).getSystemMessage());
                case "0" -> chooseMeterType(meterType, userId);
                default -> {
                    System.out.println("Undefined command");
                    addMeterReading(meterType, userId);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessages.WRONG_INPUT_FORMAT_MESSAGE.label);
            addMeterReading(meterType, userId);
        }
        chooseMeterType(meterType, userId);
    }

    public String getMeterReadingsText() {
        StringBuilder builder = new StringBuilder();
        for (MeterType meterType : meterTypeController.getAll().getBody()) {
            builder.append(meterType.getId()).append(" - ").append(meterType.getType()).append("\n");
        }
        builder.append("0").append(" - ").append("back");
        return builder.toString().trim();
    }
}
