package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meter/reading")
@Api(description = "This API is for working with meter readings", hidden = true)
public interface MeterReadingController {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added meter reading"),
            @ApiResponse(code = 400, message = "Invalid data"),
            @ApiResponse(code = 409, message = "Duplicate data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "add", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> add(@RequestBody @NonNull MeterReadingDto mr);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all meter readings"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getAll", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getAll();

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meter readings by user ID"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getByUserId", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getByUserId(@PathVariable long userId);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meter readings by user ID and meter type ID"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getByUserIdAndMeterType", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(
            @RequestParam long userId,
            @RequestParam long meterTypeId);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meter reading by ID"),
            @ApiResponse(code = 404, message = "Meter reading not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getById", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getById(@PathVariable long id);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved actual meter reading"),
            @ApiResponse(code = 404, message = "Meter reading not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getActualMeterReading", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/actual", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getActualMeterReading(@RequestParam long userId, @RequestParam long meterTypeId);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meter reading by date and meter type"),
            @ApiResponse(code = 404, message = "Meter reading not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getMeterReadingByDateAndMeterType", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(@RequestParam long userId,
                                                                      @RequestParam LocalDate date,
                                                                      @RequestParam long meterTypeId);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meter reading by month and meter type"),
            @ApiResponse(code = 404, message = "Meter reading not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getMeterReadingByMonthAndMeterType", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType
            (@RequestParam long userId, @RequestParam YearMonth yearMonth, @RequestParam long meterTypeId);
}
