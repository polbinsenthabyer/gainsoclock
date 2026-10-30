package com.gainsoclock.backend.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GymSubscriptionDto {
    private String gymSubscriptionType;
    private BigDecimal gymSubscriptionPrice;
    private Boolean isGymSubscriptionActive;
}
