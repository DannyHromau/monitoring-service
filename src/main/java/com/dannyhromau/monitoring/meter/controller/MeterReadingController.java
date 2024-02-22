package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meter/reading")
@Tag(name = "Meter reading service", description = "This API is for working with meter readings")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "404", description = "Not found")
@ApiResponse(responseCode = "401", description = "Unauthorized")
@ApiResponse(responseCode = "503", description = "Service unavailable")
@ApiResponse(responseCode = "409", description = "Duplicate data")
public interface MeterReadingController {

    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> add(@RequestBody @NonNull MeterReadingDto mr);

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getAll();

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getByUserId(@PathVariable UUID userId);

    @PreAuthorize("isAuthenticated()")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(
            @RequestParam UUID userId,
            @RequestParam UUID meterTypeId);

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getById(@PathVariable UUID id);

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/actual", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getActualMeterReading(@RequestParam UUID userId, @RequestParam UUID meterTypeId);

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(@RequestParam UUID userId,
                                                                      @RequestParam LocalDate date,
                                                                      @RequestParam UUID meterTypeId);

    @PreAuthorize("isAuthenticated()")
    @Operation(description = "Getting user details. This endpoint requires a JWT token in the Authorization header")
    @GetMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType
            (@RequestParam UUID userId, @RequestParam YearMonth yearMonth, @RequestParam UUID meterTypeId);
}
