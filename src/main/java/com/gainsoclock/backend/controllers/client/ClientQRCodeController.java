package com.gainsoclock.backend.controllers.client;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.client.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/client/qr/code")
public class ClientQRCodeController {

    private final QRCodeService qrCodeService;

    // GENERATE QR CODE
    @GetMapping("/generate")
    public ResponseDto generateQRCode(Principal principal, @RequestParam Long subscriptionId){
        return qrCodeService.generateQRCode(principal, subscriptionId);
    }

}
