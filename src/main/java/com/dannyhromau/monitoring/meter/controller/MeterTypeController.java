package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: rename entity as system
@RestController
@RequestMapping("/api/v1/system")
public interface MeterTypeController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> getMeterById(@PathVariable long id);
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterTypeDto>> getAll();
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> add(@RequestBody @NonNull MeterTypeDto meterType);
    @GetMapping(value = "/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterTypeDto> getMeterByType(@PathVariable @NonNull String type);
}
