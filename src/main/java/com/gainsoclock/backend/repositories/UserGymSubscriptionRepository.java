package com.gainsoclock.backend.repositories;

import com.gainsoclock.backend.entities.UserGymSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGymSubscriptionRepository extends JpaRepository<UserGymSubscription, Long> {

    @Query(value = """
        SELECT *
        FROM user_gym_subscription AS ugs
        WHERE   ugs.user_id = :userId
            AND ugs.gym_subscription_status = :gymSubscriptionStatus
        """,nativeQuery = true)
    Optional<UserGymSubscription> findByUserIdAndGymSubscriptionStatus(long userId, String gymSubscriptionStatus);

    List<UserGymSubscription> findAllByUserId(long userId);
}
