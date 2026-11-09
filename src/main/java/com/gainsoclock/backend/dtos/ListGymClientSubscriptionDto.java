package com.gainsoclock.backend.dtos;

import com.gainsoclock.backend.types.GymSubscriptionStatusType;
import lombok.Data;

@Data
public class ListGymClientSubscriptionDto {

    private GymSubscriptionStatusType gymSubscriptionStatusType;
    private String sortBy;
    private String sortDirection;
    private long size = 10;
    private long page = 0;
}
