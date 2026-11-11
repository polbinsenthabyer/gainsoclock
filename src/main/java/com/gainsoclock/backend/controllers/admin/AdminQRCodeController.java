package com.gainsoclock.backend.controllers.admin;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.services.client.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/qr/code")
public class AdminQRCodeController {

    private final QRCodeService qrCodeService;

    // SCAN QR CODE
    @PostMapping("/scan")
    public ResponseDto scanQRCode(Principal principal, @RequestPart("qr") MultipartFile qr){
        return qrCodeService.scanQRCode(principal, qr);
    }
}
