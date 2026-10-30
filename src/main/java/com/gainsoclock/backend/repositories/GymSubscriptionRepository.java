package com.gainsoclock.backend.repositories;

import com.gainsoclock.backend.dtos.GymSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GymSubscriptionRepository extends JpaRepository<GymSubscription, Long> {
    Optional<GymSubscription> findByGymSubscriptionType(String gymSubscriptionType);
}
