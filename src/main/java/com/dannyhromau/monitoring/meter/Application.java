package com.dannyhromau.monitoring.meter;

import com.dannyhromau.monitoring.meter.context.ApplicationContext;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        applicationContext.initialize();
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Press any key (1 - start; 0 - exit)");
            String input = in.nextLine();
            switch (input) {
                case "1" -> {
                   applicationContext.getContextHolder().getClient().start();
                }
                case "0" -> System.exit(0);
                default -> System.out.println("Undefined Command");
            }
        }
    }
}
