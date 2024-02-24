package com.dannyhromau.monitoring.system.meter.repository;

import com.dannyhromau.monitoring.system.meter.model.UserAuthority;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Daniil Hromau
 * <p>
 * This repo is using as mapper for many-to-many relationship between user
 * and authority entity
 */
@Repository
public interface UserAuthorityRepository extends ListCrudRepository<UserAuthority, UUID> {
    @Query("SELECT * FROM ms_user_authority WHERE user_id = :userId")
    List<UserAuthority> findUserAuthoritiesByUserId(@Param("userId") UUID userId);
}
