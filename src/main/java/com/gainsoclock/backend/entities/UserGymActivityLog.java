package com.gainsoclock.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gainsoclock.backend.types.UserActionTypes;
import com.gainsoclock.backend.types.UserGymMemberSubscriptionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "user_gym_activity_log")
public class UserGymActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "user_gym_membership_id")
    private long userGymMembershipId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_gym_membership_subscription_type")
    private UserGymMemberSubscriptionType userGymMembershipSubscriptionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_action")
    private UserActionTypes userActivity;

    @Column(name = "user_gym_activity_date")
    private LocalDate userGymActivityDate;

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

