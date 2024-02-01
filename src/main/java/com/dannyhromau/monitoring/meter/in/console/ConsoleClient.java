package com.dannyhromau.monitoring.meter.in.console;

import com.dannyhromau.monitoring.meter.api.ResponseEntity;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.in.console.menu.AdminMenu;
import com.dannyhromau.monitoring.meter.in.console.menu.Menu;
import com.dannyhromau.monitoring.meter.in.console.menu.UserMenu;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.model.User;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleClient {
    private final AuthController<User> ac;
    private final MeterReadingController mrc;
    private final MeterTypeController mr;
    private static final String STATUS_OK = "ok";

    public ConsoleClient(AuthController<User> ac, MeterReadingController mrc, MeterTypeController mr) {
        this.ac = ac;
        this.mrc = mrc;
        this.mr = mr;
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        System.out.println("Press any key (1 - register; 2 - login; 0 - exit)");
        switch (in.nextLine()) {
            case "1" -> register();
            case "2" -> login();
            case "0" -> System.exit(0);
            default -> {
                System.out.println("Undefined Command");
                start();
            }
        }
    }


    public void login() {
        while (true) {
            User user = new User();
            user = setLogin(user);
            user = setPassword(user);
            ResponseEntity<User> re = ac.authorize(user);
            if (re.getSystemMessage().equals(STATUS_OK)) {
                System.out.println("Successfully login");
                openMenu(re.getBody());
                break;
            } else {
                System.out.println(re.getSystemMessage());
            }
        }
    }

    public void register() {
        while (true) {
            User user = new User();
            user = setLogin(user);
            user = setPassword(user);
            ResponseEntity<Boolean> re = ac.register(user);
            if (re.getBody()) {
                System.out.println("Successfully registered");
                start();
                break;
            } else {
                System.out.println(re.getSystemMessage());
            }
        }
    }

    private User setPassword(User user) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the password or press 0 to back. The password must have greater than 7 symbols");
        String input = in.nextLine();
        if (!input.equals("0")) {
            user.setPassword(input);
        } else {
            start();
        }
        return user;
    }

    public User setLogin(User user) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the login or press 0 to back. The login must have greater than 2 symbols");
        String input = in.nextLine();
        if (!input.equals("0")) {
            user.setLogin(input);
        } else {
            start();
        }
        return user;
    }

    private void openMenu(User user) {
        Optional<Authority> authority = user.getAuthorities()
                .stream()
                .filter(a -> a.getName().equals("admin"))
                .findFirst();
        Menu menu = authority.isPresent() ?
                new AdminMenu(user, mrc, this, mr) : new UserMenu(user, mrc, this, mr);
        menu.launch();
    }
}
