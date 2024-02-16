package com.dannyhromau.monitoring.meter.security.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
public class PermissionConfig {
    private Unauthorized unauthorized;
    public static class Unauthorized {
        private List<String> urls;

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }
    }
}
