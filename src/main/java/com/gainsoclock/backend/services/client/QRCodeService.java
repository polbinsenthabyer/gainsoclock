package com.gainsoclock.backend.services.client;

import com.gainsoclock.backend.dtos.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface QRCodeService {

    ResponseDto generateQRCode(Principal principal, Long subscriptionId);

    ResponseDto scanQRCode(Principal principal, MultipartFile qr);

}
