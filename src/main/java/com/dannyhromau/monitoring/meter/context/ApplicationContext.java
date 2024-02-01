package com.dannyhromau.monitoring.meter.context;

import com.dannyhromau.monitoring.meter.core.util.MeterTypesLoader;
import com.dannyhromau.monitoring.meter.model.Authority;
import com.dannyhromau.monitoring.meter.model.User;
import com.dannyhromau.monitoring.meter.repository.AuthorityRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ApplicationContext {
    private ApplicationContextHolder contextHolder = new ApplicationContextHolder();
    private static volatile ApplicationContext instance;
    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            synchronized (ApplicationContext.class) {
                if (instance == null) {
                    instance = new ApplicationContext();
                }
            }
        }
        return instance;
    }

    public ApplicationContextHolder getContextHolder() {
        return contextHolder;
    }

    public void initialize() {
        contextHolder.setupContext();
        prepareUserData(contextHolder.getAuthorityRepository());
        prepareMeterTypes();
    }

    public void prepareUserData(AuthorityRepository ar) {
        List<Authority> authorities = new LinkedList<>();
        Authority adminAuthority = new Authority();
        adminAuthority.setName("admin");
        Authority userAuthority = new Authority();
        userAuthority.setName("user");
        authorities.add(adminAuthority);
        authorities.add(userAuthority);
        ar.addAll(authorities);
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setAuthorities(authorities);
        admin.setDeleted(false);
        Optional<User> adminOpt = contextHolder.getUserRepository().findUserByLogin(admin.getLogin());
        adminOpt.ifPresent(user -> contextHolder.getUserRepository().deleteById(user.getId()));
        contextHolder.getUserRepository().add(admin);
    }

    public void prepareMeterTypes() {
        MeterTypesLoader loader = new MeterTypesLoader();
        loader.load();
    }
}
