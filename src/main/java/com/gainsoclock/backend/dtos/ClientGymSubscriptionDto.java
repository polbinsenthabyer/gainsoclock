package com.gainsoclock.backend.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ClientGymSubscriptionDto {
    private String gymSubscriptionType;
    private Date gymSubscriptionRequestStartDate;
    private Date gymSubscriptionRequestEndDate;
}
