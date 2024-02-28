package com.dannyhromau.monitoring.meter;

import com.dannyhromau.monitoring.system.meter.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
@TestPropertySource("classpath:application-test.yml")
class SpringBootBackendApplicationTests {

    @Test
    void contextLoads() {
        assertThat(SecurityContextHolder.getContext()).isNotNull();
    }

}
