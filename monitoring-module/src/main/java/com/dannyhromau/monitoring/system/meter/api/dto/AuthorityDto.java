package com.dannyhromau.monitoring.system.meter.api.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.UUID;

@Getter
@Setter
public class AuthorityDto {
    private UUID id;
    @NonNull
    @NotBlank(message = "Name must not be blank")
    private String name;

}
