package com.dannyhromau.monitoring.meter.controller;


import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: add advice controller for catching exceptions
@RestController
@RequestMapping("/api/v1/auth")
@Api(description = "This API is for authorization and authentication processes", hidden = true)
public interface AuthController<T> {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful registration"),
            @ApiResponse(code = 400, message = "Invalid data"),
            @ApiResponse(code = 409, message = "Duplicate data"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Entity not found")})
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> register(@RequestBody @NonNull AuthDto user);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful authorization"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<T> authorize(@RequestBody @NonNull AuthDto user);
}
