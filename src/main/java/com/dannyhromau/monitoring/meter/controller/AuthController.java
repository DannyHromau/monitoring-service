package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public interface AuthController<T> {

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> register(@RequestBody @NonNull AuthDto user);

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<T> authorize(@RequestBody @NonNull AuthDto user);
}
