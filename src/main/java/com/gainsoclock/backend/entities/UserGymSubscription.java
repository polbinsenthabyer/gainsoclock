package com.gainsoclock.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gainsoclock.backend.types.GymSubscriptionStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserGymSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "gym_subscription_type")
    private String gymSubscriptionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "gym_subscription_status")
    private GymSubscriptionStatusType gymSubscriptionStatus;

    @Column(name = "gym_subscription_start_date")
    private Date gymSubscriptionStartDate;

    @Column(name = "gym_subscription_end_date")
    private Date gymSubscriptionEndDate;

    @Column(name = "snapshot")
    private String snapshot;

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

