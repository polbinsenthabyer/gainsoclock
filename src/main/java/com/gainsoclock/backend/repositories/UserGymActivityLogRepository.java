package com.gainsoclock.backend.repositories;

import com.gainsoclock.backend.entities.UserGymActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserGymActivityLogRepository extends JpaRepository<UserGymActivityLog, Long> {
    Optional<UserGymActivityLog> findByUserIdAndUserGymActivityDate(long userId, LocalDate date);
}
