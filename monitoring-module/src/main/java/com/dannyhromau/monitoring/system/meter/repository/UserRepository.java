package com.dannyhromau.monitoring.system.meter.repository;

import com.dannyhromau.monitoring.system.meter.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends ListCrudRepository<User, UUID> {

    @Query("SELECT * FROM ms_user WHERE login = :login")
    Optional<User> findByLogin(@Param("login") String login);
}
