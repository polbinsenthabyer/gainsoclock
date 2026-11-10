package com.gainsoclock.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "user_gym_subscription_qr")
public class UserGymSubscriptionQr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_gym_subscription_qr_id")
    private long id;

    @Column(name = "user_gym_subscription_id")
    private long userGymSubscriptionId;

    @Column(name = "user_gym_subscription_qr_token")
    private String userGymSubscriptionQRToken;

    @Column(name = "is_qr_token_active")
    private Boolean isQRTokenActive;

    @Column(name = "created_by")
    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "updated_at")
    private Date updatedAt;
}
