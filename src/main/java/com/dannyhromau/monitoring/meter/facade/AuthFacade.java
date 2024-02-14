package com.dannyhromau.monitoring.meter.facade;

import com.dannyhromau.monitoring.meter.exception.DuplicateDataException;
import com.dannyhromau.monitoring.meter.exception.EntityNotFoundException;
import com.dannyhromau.monitoring.meter.exception.InvalidDataException;
import com.dannyhromau.monitoring.meter.exception.UnAuthorizedException;

import java.sql.SQLException;

/**
 * @author Daniil Hromau
 *
 * Facade is using as converting layer between controller and service to separate the logic of working with
 * dto and entity and mapper's injection
 *
 */
public interface AuthFacade<T> {
    /**
     *
     * @param authDto dto with credentials
     * @return authenticated item
     * @throws DuplicateDataException when data exists in DB
     * @throws SQLException if something goes wrong when it's working with DB
     * @throws InvalidDataException when invalid data references by client
     * @throws EntityNotFoundException when entity doesn't exist in DB
     */
    T register(T authDto) throws DuplicateDataException, SQLException, InvalidDataException, EntityNotFoundException;

    /**
     *
     * @param authDto dto with credentials
     * @return authenticated item
     * @throws SQLException if something goes wrong when it's working with DB
     * @throws UnAuthorizedException when referenced credentials are invalid
     */
    T authorize(T authDto) throws SQLException, UnAuthorizedException;
}
