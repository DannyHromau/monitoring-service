package com.dannyhromau.monitoring.system.meter.api.dto;

import com.dannyhromau.monitoring.system.meter.model.Authority;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private UUID id;
    @NonNull
    @NotBlank(message = "Login must not be blank")
    private String login;
    @NonNull
    @NotBlank(message = "Password must not be blank")
    private String password;
    @NotBlank(message = "Boolean value must not be null")
    private boolean isDeleted;
    private List<Authority> authorities;
}
