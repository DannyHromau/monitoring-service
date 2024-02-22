package com.dannyhromau.monitoring.meter.controller;

import com.dannyhromau.monitoring.meter.api.dto.MeterTypeDto;
import com.dannyhromau.monitoring.meter.controller.impl.MeterTypeControllerImpl;
import com.dannyhromau.monitoring.meter.core.util.ErrorMessages;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.facade.MeterTypeFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@DisplayName("Testing of meter_type_controller")
class MeterTypeControllerImplTest {
    @InjectMocks
    private MeterTypeControllerImpl meterTypeController;

    @Mock
    private MeterTypeFacade meterTypeFacade;


    @Test
    @DisplayName("Get meter type by id when exists")
    void getMeterTypeByIDWhenExists() throws EntityNotFoundException, SQLException {
        UUID id = UUID.randomUUID();
        MeterTypeDto meterTypeDto = new MeterTypeDto();
        when(meterTypeFacade.getMeterById(UUID.randomUUID())).thenReturn(meterTypeDto);
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
        ResponseEntity<MeterTypeDto> response = meterTypeController.add(meterTypeDto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
