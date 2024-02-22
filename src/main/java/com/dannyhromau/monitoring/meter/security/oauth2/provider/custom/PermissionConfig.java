package com.dannyhromau.monitoring.meter.security.oauth2.provider.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "permission")
public class PermissionConfig {
    private RoleProperties unauthorized;
    private RoleProperties admin;

    public static class RoleProperties {
        private List<String> urls;

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }
    }
}
