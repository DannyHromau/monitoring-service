package com.dannyhromau.monitoring.meter.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class ValidatorConfig {
    private Credential credential;
    public static class Credential {
        private String emailPattern;
        private String passwordPattern;

        public String getEmailPattern() {
            return emailPattern;
        }

        public void setEmailPattern(String emailPattern) {
            this.emailPattern = emailPattern;
        }

        public String getPasswordPattern() {
            return passwordPattern;
        }

        public void setPasswordPattern(String passwordPattern) {
            this.passwordPattern = passwordPattern;
        }
    }
}
