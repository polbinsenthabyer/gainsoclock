package com.gainsoclock.backend.utilities;


import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserGymSubscription;
import com.gainsoclock.backend.entities.UserGymSubscriptionQr;
import com.gainsoclock.backend.repositories.UserGymSubscriptionQrRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class QRCodeUtil {

    private final UserGymSubscriptionQrRepository userGymSubscriptionQrRepository;
    private static final SecureRandom RANDOM_TOKEN = new SecureRandom();
    private static final QRCodeWriter QR_CODE_WRITER = new QRCodeWriter();
    private static final ByteArrayOutputStream OUTPUT = new ByteArrayOutputStream();

    public String processQRCode(User user, UserGymSubscription request){
        UserGymSubscriptionQr qr = new UserGymSubscriptionQr();
        String token = generateQRToken();

        qr.setUserGymSubscriptionId(request.getId());
        qr.setUserGymSubscriptionQRToken(token);
        qr.setIsQRTokenActive(Boolean.TRUE);
        qr.setCreatedAt(new Date());
        qr.setCreatedBy(user.getUsername());
        qr.setUpdatedAt(new Date());
        qr.setUpdatedBy(user.getUsername());
        userGymSubscriptionQrRepository.save(qr);

        return token;
    }

    public ResponseDto generateQRCode(String token) {
        try {
            BitMatrix matrix = QR_CODE_WRITER.encode(token, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToStream(matrix, "PNG", OUTPUT);
            return ResponseUtility.getSuccessResponse(MessageType.SUCCESSFULLY_GENERATED_QR_CODE, OUTPUT.toByteArray());
        } catch (WriterException | IOException e){
            return ResponseUtility.getSuccessResponse(MessageType.ERROR_FAILED_TO_GENERATE_QR_CODE);
        }
    }

    private String generateQRToken() {
        byte[] bytes = new byte[32];
        RANDOM_TOKEN.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
