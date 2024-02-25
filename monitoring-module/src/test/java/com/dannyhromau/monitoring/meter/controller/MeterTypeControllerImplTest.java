package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.system.meter.Application;
import com.dannyhromau.monitoring.system.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.system.meter.controller.impl.MeterTypeControllerImpl;
import com.dannyhromau.monitoring.system.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.system.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.system.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.system.meter.facade.MeterTypeFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
@DisplayName("Testing of meter_type_controller")
class MeterTypeControllerImplTest {
    @InjectMocks
    private MeterTypeControllerImpl meterTypeController;

    @Mock
    private MeterTypeFacade meterTypeFacade;


    @Test
    @DisplayName("Get meter type by id when exists")
    void getMeterTypeByIDWhenExists() throws EntityNotFoundException {
        UUID id = UUID.randomUUID();
        MeterTypeDto meterTypeDto = new MeterTypeDto();
        when(meterTypeFacade.getMeterById(id)).thenReturn(meterTypeDto);
        ResponseEntity<MeterTypeDto> response = meterTypeController.getMeterById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(meterTypeDto, response.getBody());
    }

    @Test
    @DisplayName("Get all meter types")
    void getAll_ReturnsAllMeterTypes() throws SQLException {
        List<MeterTypeDto> meterTypes = new ArrayList<>();
        when(meterTypeFacade.getAll()).thenReturn(meterTypes);
        ResponseEntity<List<MeterTypeDto>> response = meterTypeController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(meterTypes, response.getBody());
    }

    @Test
    @DisplayName("Add meter type when not exists")
    void addMeterTypeWhenNotExists() throws DuplicateDataException, SQLException {
        MeterTypeDto meterTypeDto = new MeterTypeDto();
        when(meterTypeFacade.add(meterTypeDto)).thenReturn(meterTypeDto);
        ResponseEntity<MeterTypeDto> response = meterTypeController.add(meterTypeDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(meterTypeDto, response.getBody());
    }

    @Test
    @DisplayName("Add meter type when exists")
    void addMeterTypeWhenExists() throws DuplicateDataException, SQLException {
        MeterTypeDto meterTypeDto = new MeterTypeDto();
        when(meterTypeFacade.add(meterTypeDto))
                .thenThrow(new DuplicateDataException(ErrorMessages.DUPLICATED_DATA_MESSAGE.label));
        assertThatExceptionOfType(DuplicateDataException.class)
                .isThrownBy(() -> meterTypeFacade.add(meterTypeDto))
                .withMessage(ErrorMessages.DUPLICATED_DATA_MESSAGE.label);
    }
}
