package com.dannyhromau.monitoring.meter.context;

import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.controller.impl.AuthControllerImpl;
import com.dannyhromau.monitoring.meter.controller.impl.MeterTypeControllerImpl;
import com.dannyhromau.monitoring.meter.controller.impl.MeterReadingControllerImpl;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;
import com.dannyhromau.monitoring.meter.repository.UserRepository;
import com.dannyhromau.monitoring.meter.repository.impl.AuthorityRepositoryImpl;
import com.dannyhromau.monitoring.meter.repository.impl.MeterReadingRepositoryImpl;
import com.dannyhromau.monitoring.meter.repository.impl.MeterTypeRepositoryImpl;
import com.dannyhromau.monitoring.meter.repository.impl.UserRepositoryImpl;
import com.dannyhromau.monitoring.meter.service.*;
import com.dannyhromau.monitoring.meter.service.impl.*;
import com.dannyhromau.monitoring.meter.in.console.ConsoleClient;

public class ApplicationContextHolder {
    private AuthorityRepository authorityRepository;
    private MeterReadingRepository mrReadingRepository;
    private UserRepository userRepository;
    private AuthorityService authorityService;
    private UserService userService;
    private AuthService<User> authService;
    private MeterReadingService mrService;
    private AuthController<User> authController;
    private MeterReadingController mrController;
    private MeterTypeRepository meterTypeRepository;
    private MeterTypeService meterTypeService;
    private MeterTypeController meterTypeController;
    private ConsoleClient client;

    public AuthorityRepository getAuthorityRepository() {
        return authorityRepository;
    }

    public MeterReadingRepository getMrReadingRepository() {
        return mrReadingRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AuthorityService getAuthorityService() {
        return authorityService;
    }

    public UserService getUserService() {
        return userService;
    }

    public AuthService<User> getAuthService() {
        return authService;
    }

    public MeterReadingService getMrService() {
        return mrService;
    }

    public AuthController<User> getAuthController() {
        return authController;
    }

    public MeterReadingController getMrController() {
        return mrController;
    }

    public MeterTypeRepository getMeterTypeRepository() {
        return meterTypeRepository;
    }

    public MeterTypeService getMeterTypeService() {
        return meterTypeService;
    }

    public MeterTypeController getMeterTypeController() {
        return meterTypeController;
    }

    public ConsoleClient getClient() {
        return client;
    }

    public void setupContext() {
        authorityRepository = new AuthorityRepositoryImpl();
        mrReadingRepository = new MeterReadingRepositoryImpl();
        userRepository = new UserRepositoryImpl();
        authorityService = new AuthorityServiceImpl(authorityRepository);
        userService = new UserServiceImpl(userRepository);
        authService = new ConsoleAuthServiceImpl(userService, authorityService);
        mrService = new MeterReadingServiceImpl(mrReadingRepository);
        authController = new AuthControllerImpl(authService);
        mrController = new MeterReadingControllerImpl(mrService);
        meterTypeRepository = new MeterTypeRepositoryImpl();
        meterTypeService = new MeterTypeServiceImpl(meterTypeRepository);
        meterTypeController = new MeterTypeControllerImpl(meterTypeService);
        client = new ConsoleClient(authController, mrController, meterTypeController);
    }
}
