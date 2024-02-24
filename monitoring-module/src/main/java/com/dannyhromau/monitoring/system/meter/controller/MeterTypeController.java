package com.dannyhromau.monitoring.system.meter.controller;


import com.dannyhromau.monitoring.system.meter.api.dto.MeterTypeDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO: rename entity as system
@RestController
@RequestMapping("/api/v1/system")
@Tag(name = "Meter reading service", description = "This API is for working with meter system types")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "404", description = "Not found")
@ApiResponse(responseCode = "401", description = "Unauthorized")
@ApiResponse(responseCode = "503", description = "Service unavailable")
@ApiResponse(responseCode = "409", description = "Duplicate data")
public interface MeterTypeController {


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> getMeterById(@PathVariable UUID id);

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterTypeDto>> getAll();

    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> add(@RequestBody @NonNull MeterTypeDto meterType);

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> getMeterByType(@PathVariable @NonNull String type);
}
