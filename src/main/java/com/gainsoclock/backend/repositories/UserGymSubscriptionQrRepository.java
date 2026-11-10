package com.gainsoclock.backend.repositories;

import com.gainsoclock.backend.entities.UserGymSubscriptionQr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGymSubscriptionQrRepository extends JpaRepository<UserGymSubscriptionQr, Long> {

    Optional<UserGymSubscriptionQr> findByUserGymSubscriptionId(long id);

    Optional<UserGymSubscriptionQr> findByUserGymSubscriptionQRToken(String qrToken);
}
