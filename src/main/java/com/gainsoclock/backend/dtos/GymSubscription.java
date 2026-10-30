package com.gainsoclock.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "gym_subscription")
public class GymSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "gym_subscription_type")
    private String gymSubscriptionType;

    @Column(name = "gym_subscription_price")
    private BigDecimal gymSubscriptionPrice;

    @Column(name = "is_gym_subscription_active")
    private Boolean isGymSubscriptionActive;

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
