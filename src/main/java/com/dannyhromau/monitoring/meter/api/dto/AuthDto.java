package com.dannyhromau.monitoring.meter.api.dto;

import com.dannyhromau.monitoring.meter.model.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private Long id;
    @NonNull
    @NotBlank(message = "Login must not be blank")
    private String login;
    @NonNull
    @NotBlank(message = "Password must not be blank")
    private String password;
        @JsonProperty("isDeleted")
        @NotBlank(message = "Boolean value must not be null")
    private boolean isDeleted;
    private List<Authority> authorities;
}
