package com.gainsoclock.backend.controllers.client;

import com.gainsoclock.backend.dtos.ClientGymSubscriptionDto;
import com.gainsoclock.backend.dtos.ClientGymSubscriptionListDto;
import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.client.ClientGymSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/client/gym/subscription")
public class ClientGymSubscriptionController {

    private final ClientGymSubscriptionService clientGymSubscriptionService;

    @PostMapping("/request")
    public ResponseDto requestGymSubscription(Principal principal, @RequestBody ClientGymSubscriptionDto dto){
        return clientGymSubscriptionService.requestGymSubscription(principal, dto);
    }

    @GetMapping("/detail")
    public ResponseDto detailGymSubscription(Principal principal){
        return clientGymSubscriptionService.detailGymSubscription(principal);
    }

    @GetMapping("/list")
    public ResponseDto listGymSubscription(Principal principal, ClientGymSubscriptionListDto dto){
        return clientGymSubscriptionService.listGymSubscription(principal, dto);
    }

}
