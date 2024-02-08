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
import com.dannyhromau.monitoring.meter.db.migration.LiquibaseRunner;
import com.dannyhromau.monitoring.meter.facade.AuthFacade;
import com.dannyhromau.monitoring.meter.facade.MeterReadingFacade;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import com.dannyhromau.monitoring.meter.facade.impl.AuthFacadeImpl;
import com.dannyhromau.monitoring.meter.facade.impl.MeterReadingFacadeImpl;
import com.dannyhromau.monitoring.meter.facade.impl.MeterTypeFacadeImpl;
import com.dannyhromau.monitoring.meter.in.console.ConsoleClient;
import com.dannyhromau.monitoring.meter.mapper.MeterReadingMapper;
import com.dannyhromau.monitoring.meter.mapper.MeterTypeMapper;
import com.dannyhromau.monitoring.meter.mapper.UserMapper;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.model.audit.UserAudit;
import com.dannyhromau.monitoring.meter.repository.*;
import com.dannyhromau.monitoring.meter.repository.impl.jdbc.*;
import com.dannyhromau.monitoring.meter.service.*;
import com.dannyhromau.monitoring.meter.service.impl.*;
import lombok.Getter;

@Getter
public class ApplicationContextHolder {
    private AuthorityRepository authorityRepository;
    private MeterReadingRepository mrReadingRepository;
    private UserRepository userRepository;
    private AuthorityService authorityService;
    private UserService userService;
    private AuthService<User> authService;
    private MeterReadingService mrService;
    private AuthController<AuthDto> authController;
    private MeterReadingController mrController;
    private MeterTypeRepository meterTypeRepository;
    private MeterTypeService meterTypeService;
    private MeterTypeController meterTypeController;
    private ConsoleClient client;
    private JdbcUtil jdbcUtil;
    private JdbcUtil jdbcLiquibaseUtil;
    private JdbcUtil jdbcAuditUtil;
    private AuditRepository<UserAudit> auditRepository;
    private AuditService<UserAudit> userAuditService;
    private LiquibaseConfig liquibaseConfig;
    private AuthFacade<AuthDto> authFacade;
    private MeterReadingFacade meterReadingFacade;
    private MeterTypeFacade meterTypeFacade;

    //TODO: check using factory for jdbc utils
    public void setupContext() {
        liquibaseConfig = new LiquibaseConfig();
        jdbcUtil = new JdbcUtil(liquibaseConfig
                .getProperty(LiquibaseConfig.SCHEMA_MAIN, "db/liquibase.properties"));
        jdbcLiquibaseUtil = new JdbcUtil(liquibaseConfig
                .getProperty(LiquibaseConfig.SCHEMA_SYSTEM, "db/liquibase.properties"));
        jdbcAuditUtil = new JdbcUtil(liquibaseConfig
                .getProperty(LiquibaseConfig.SCHEMA_AUDIT, "db/liquibase.properties"));
        LiquibaseRunner runner = new LiquibaseRunner(jdbcLiquibaseUtil);
        runner.run(liquibaseConfig.getProperty(LiquibaseConfig.MASTER_PATH, "db/liquibase.properties"));
        auditRepository = new JdbcUserAuditRepository(jdbcAuditUtil);
        userAuditService = new AuditServiceImpl(auditRepository);
        authorityRepository = new JdbcAuthorityRepository(jdbcUtil);
        mrReadingRepository = new JdbcMeterReadingRepository(jdbcUtil);
        userRepository = new JdbcUserRepository(jdbcUtil);
        authorityService = new AuthorityServiceImpl(authorityRepository);
        userService = new UserServiceImpl(userRepository);
        authService = new ConsoleAuthServiceImpl(userService, authorityService);
        mrService = new MeterReadingServiceImpl(mrReadingRepository);
        authFacade = new AuthFacadeImpl(authService, UserMapper.INSTANCE);
        authController = new AuthControllerImpl(authFacade);
        meterReadingFacade = new MeterReadingFacadeImpl(mrService, MeterReadingMapper.INSTANCE);
        mrController = new MeterReadingControllerImpl(meterReadingFacade);
        meterTypeRepository = new JdbcMeterTypeRepository(jdbcUtil);
        meterTypeService = new MeterTypeServiceImpl(meterTypeRepository);
        meterTypeFacade = new MeterTypeFacadeImpl(meterTypeService, MeterTypeMapper.INSTANCE);
        meterTypeController = new MeterTypeControllerImpl(meterTypeFacade);
        client = new ConsoleClient(authController, mrController, meterTypeController);
    }
}
