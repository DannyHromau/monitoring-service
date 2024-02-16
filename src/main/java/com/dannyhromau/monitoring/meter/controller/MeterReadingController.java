package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.api.dto.MeterReadingDto;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meter/reading")
public interface MeterReadingController {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> add(@RequestBody @NonNull MeterReadingDto mr);

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getAll();

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getByUserId(@PathVariable long userId);
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MeterReadingDto>> getByUserIdAndMeterType(
            @RequestParam long userId,
            @RequestParam long meterTypeId);

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getById(@PathVariable long id);

    @GetMapping(value = "/actual", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getActualMeterReading(@RequestParam long userId, @RequestParam long meterTypeId);

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getMeterReadingByDateAndMeterType(@RequestParam long userId,
                                                                      @RequestParam LocalDate date,
                                                                      @RequestParam long meterTypeId);
    @GetMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MeterReadingDto> getMeterReadingByMonthAndMeterType
            (@RequestParam long userId, @RequestParam YearMonth yearMonth, @RequestParam long meterTypeId);
}
