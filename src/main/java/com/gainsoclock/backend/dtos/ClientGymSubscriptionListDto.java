package com.gainsoclock.backend.dtos;

import lombok.Data;

@Data
public class ClientGymSubscriptionListDto {
    private long size = 10;
    private long page = 0;
}
