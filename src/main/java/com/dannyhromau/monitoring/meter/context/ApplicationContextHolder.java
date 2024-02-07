package com.dannyhromau.monitoring.meter.context;

import com.dannyhromau.monitoring.meter.controller.AuthController;
import com.dannyhromau.monitoring.meter.controller.MeterReadingController;
import com.dannyhromau.monitoring.meter.controller.MeterTypeController;
import com.dannyhromau.monitoring.meter.controller.impl.AuthControllerImpl;
import com.dannyhromau.monitoring.meter.controller.impl.MeterReadingControllerImpl;
import com.dannyhromau.monitoring.meter.controller.impl.MeterTypeControllerImpl;
import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.db.migration.LiquibaseRunner;
import com.dannyhromau.monitoring.meter.in.console.ConsoleClient;
import com.dannyhromau.monitoring.meter.model.JdbcUserAudit;
import com.dannyhromau.monitoring.meter.model.User;
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
    private AuthController<User> authController;
    private MeterReadingController mrController;
    private MeterTypeRepository meterTypeRepository;
    private MeterTypeService meterTypeService;
    private MeterTypeController meterTypeController;
    private ConsoleClient client;
    private JdbcUtil jdbcUtil;
    private JdbcUtil jdbcLiquibaseUtil;
    private JdbcUtil jdbcAuditUtil;
    private AuditRepository<JdbcUserAudit> auditRepository;
    private AuditService<JdbcUserAudit> userAuditService;

    //TODO: check using factory for jdbc utils
    public void setupContext() {
        String liquibasePath = "db/settings.jdbc.properties";
        String dbConnectionPath = "db/main.jdbc.properties";
        String changeLogPath = "db/changelog/changelog-v1.0-cumulative.xml";
        String auditPath = "db/audit.jdbc.properties";
        jdbcUtil = new JdbcUtil(dbConnectionPath);
        jdbcLiquibaseUtil = new JdbcUtil(liquibasePath);
        jdbcAuditUtil = new JdbcUtil(auditPath);
        LiquibaseRunner runner = new LiquibaseRunner(jdbcLiquibaseUtil);
        runner.run(changeLogPath);
        auditRepository = new JdbcUserAuditRepository(jdbcAuditUtil);
        userAuditService = new AuditServiceImpl(auditRepository);
        authorityRepository = new JdbcAuthorityRepository(jdbcUtil);
        mrReadingRepository = new JdbcMeterReadingRepository(jdbcUtil);
        userRepository = new JdbcUserRepository(jdbcUtil);
        authorityService = new AuthorityServiceImpl(authorityRepository);
        userService = new UserServiceImpl(userRepository);
        authService = new ConsoleAuthServiceImpl(userService, authorityService);
        mrService = new MeterReadingServiceImpl(mrReadingRepository);
        authController = new AuthControllerImpl(authService, userAuditService);
        mrController = new MeterReadingControllerImpl(mrService, userAuditService);
        meterTypeRepository = new JdbcMeterTypeRepository(jdbcUtil);
        meterTypeService = new MeterTypeServiceImpl(meterTypeRepository);
        meterTypeController = new MeterTypeControllerImpl(meterTypeService);
        client = new ConsoleClient(authController, mrController, meterTypeController);
    }
}
