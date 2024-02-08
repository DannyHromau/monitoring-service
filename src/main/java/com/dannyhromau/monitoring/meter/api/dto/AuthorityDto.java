package com.dannyhromau.monitoring.meter.api.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthorityDto {
    private Long id;
    @NonNull
    @NotBlank(message = "Name must not be blank")
    private String name;

}
