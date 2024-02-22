package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.api.dto.AuthDto;
import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author Daniil Hromau
 * <p>
 * Facade is using as converting layer between controller and service to separate the logic of working with
 * dto and entity and mapper's injection
 */
@Component
public interface AuthFacade<T> {
    /**
     * @param authDto dto with credentials
     * @return authenticated item
     * @throws DuplicateDataException  when data exists in DB
     * @throws SQLException            if something goes wrong when it's working with DB
     * @throws InvalidDataException    when invalid data references by client
     * @throws EntityNotFoundException when entity doesn't exist in DB
     */
    Boolean register(AuthDto authDto);

    /**
     * @param authDto dto with credentials
     * @return authenticated item
     * @throws SQLException          if something goes wrong when it's working with DB
     * @throws UnAuthorizedException when referenced credentials are invalid
     */
    T authorize(AuthDto authDto);
}
