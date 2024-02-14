package com.dannyhromau.monitoring.meter.context;

import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.controller.impl.AuthControllerImpl;
import com.dannyhromau.monitoring.meter.controller.impl.MeterReadingControllerImpl;
import com.dannyhromau.monitoring.meter.controller.impl.MeterTypeControllerImpl;
import com.dannyhromau.monitoring.meter.core.config.LiquibaseConfig;
import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.core.util.MeterTypesLoader;
import com.dannyhromau.monitoring.meter.db.migration.LiquibaseRunner;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import com.dannyhromau.monitoring.meter.facade.impl.AuthFacadeImpl;
import com.dannyhromau.monitoring.meter.facade.impl.MeterReadingFacadeImpl;
import com.dannyhromau.monitoring.meter.facade.impl.MeterTypeFacadeImpl;
import com.dannyhromau.monitoring.meter.mapper.MeterReadingMapper;
import com.dannyhromau.monitoring.meter.mapper.MeterTypeMapper;
import com.dannyhromau.monitoring.meter.mapper.UserMapper;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;
import com.dannyhromau.monitoring.meter.repository.MeterReadingRepository;
import com.dannyhromau.monitoring.meter.repository.MeterTypeRepository;
import com.dannyhromau.monitoring.meter.repository.UserRepository;
import com.dannyhromau.monitoring.meter.repository.impl.jdbc.JdbcAuthorityRepository;
import com.dannyhromau.monitoring.meter.repository.impl.jdbc.JdbcMeterReadingRepository;
import com.dannyhromau.monitoring.meter.repository.impl.jdbc.JdbcMeterTypeRepository;
import com.dannyhromau.monitoring.meter.repository.impl.jdbc.JdbcUserRepository;
import com.dannyhromau.monitoring.meter.service.*;
import com.dannyhromau.monitoring.meter.service.impl.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private LiquibaseConfig liquibaseConfig;
    private MeterTypeRepository meterTypeRepository;
    private MeterTypeService meterTypeService;
    private MeterTypeFacade meterTypeFacade;
    private MeterTypeController meterTypeController;
    private UserRepository userRepository;
    private UserService userService;
    private MeterReadingRepository meterReadingRepository;
    private MeterReadingService meterReadingService;
    private MeterReadingFacade meterReadingFacade;
    private MeterReadingController meterReadingController;
    private AuthService<User> authService;
    private AuthFacade<AuthDto> authFacade;
    private AuthController<AuthDto> authController;
    private AuthorityRepository authorityRepository;
    private AuthorityService authorityService;
    private JdbcUtil jdbcLiquibaseUtil;
    private JdbcUtil jdbcUtil;
    private JdbcUtil auditJdbcUtil;
    private MeterTypesLoader meterTypesLoader;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        liquibaseConfig = new LiquibaseConfig();
         jdbcUtil = new JdbcUtil(liquibaseConfig
                .getProperty(LiquibaseConfig.SCHEMA_MAIN, "db/liquibase.properties"));
        auditJdbcUtil = new JdbcUtil(liquibaseConfig
                .getProperty(LiquibaseConfig.SCHEMA_AUDIT, "db/liquibase.properties"));
        meterTypeRepository = new JdbcMeterTypeRepository(jdbcUtil);
        meterTypeService = new MeterTypeServiceImpl(meterTypeRepository);
        meterTypeFacade = new MeterTypeFacadeImpl(meterTypeService, MeterTypeMapper.INSTANCE);
        meterTypeController = new MeterTypeControllerImpl(meterTypeFacade);
        meterReadingRepository = new JdbcMeterReadingRepository(jdbcUtil);
        meterReadingService = new MeterReadingServiceImpl(meterReadingRepository);
        meterReadingFacade = new MeterReadingFacadeImpl(meterReadingService, MeterReadingMapper.INSTANCE);
        meterReadingController = new MeterReadingControllerImpl(meterReadingFacade);
        userRepository = new JdbcUserRepository(jdbcUtil);
        userService = new UserServiceImpl(userRepository);
        authorityRepository = new JdbcAuthorityRepository(jdbcUtil);
        authorityService = new AuthorityServiceImpl(authorityRepository);
        authService = new ConsoleAuthServiceImpl(userService, authorityService);
        authFacade = new AuthFacadeImpl(authService, UserMapper.INSTANCE);
        authController = new AuthControllerImpl(authFacade);
        meterTypesLoader = new MeterTypesLoader(meterTypeRepository);
        jdbcLiquibaseUtil = new JdbcUtil(liquibaseConfig
                .getProperty(LiquibaseConfig.SCHEMA_SYSTEM, "db/liquibase.properties"));
        LiquibaseRunner runner = new LiquibaseRunner(jdbcLiquibaseUtil);
        runner.run(liquibaseConfig.getProperty(LiquibaseConfig.MASTER_PATH, "db/liquibase.properties"));
        meterTypesLoader.load();
        servletContext.setAttribute("meterTypeController", meterTypeController);
        servletContext.setAttribute("meterReadingController", meterReadingController);
        servletContext.setAttribute("authController", authController);
        servletContext.setAttribute("liquibaseConfig", liquibaseConfig);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
