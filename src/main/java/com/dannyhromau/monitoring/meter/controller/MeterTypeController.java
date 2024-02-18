package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: add advice controller for catching exceptions
//TODO: rename entity as system
@RestController
@RequestMapping("/api/v1/system")
@Api(description = "This API is for working with meter system types", hidden = true)
public interface MeterTypeController {


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meter type by ID"),
            @ApiResponse(code = 404, message = "Meter type not found"),
            @ApiResponse(code = 500, message = "Internal server error")

    })
    @ApiOperation(value = "getMeterById", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> getMeterById(@PathVariable long id);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all meter types"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getAll", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterTypeDto>> getAll();

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added meter type"),
            @ApiResponse(code = 409, message = "Duplicate data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "add", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> add(@RequestBody @NonNull MeterTypeDto meterType);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved meter type by type"),
            @ApiResponse(code = 404, message = "Meter type not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @ApiOperation(value = "getMeterByType", authorizations = {
            @Authorization(value = "JWT Bearer Token")
    })
    @GetMapping(value = "/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> getMeterByType(@PathVariable @NonNull String type);
}
