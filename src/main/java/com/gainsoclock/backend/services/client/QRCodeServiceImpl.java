package com.gainsoclock.backend.services.client;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.entities.User;
import com.gainsoclock.backend.entities.UserGymSubscription;
import com.gainsoclock.backend.entities.UserGymSubscriptionQr;
import com.gainsoclock.backend.repositories.UserGymActivityLogRepository;
import com.gainsoclock.backend.repositories.UserGymSubscriptionQrRepository;
import com.gainsoclock.backend.repositories.UserGymSubscriptionRepository;
import com.gainsoclock.backend.utilities.*;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements  QRCodeService {

    private final UserGymSubscriptionQrRepository userGymSubscriptionQrRepository;
    private final UserGymSubscriptionRepository userGymSubscriptionRepository;
    private final UserActivityUtil userActivityUtil;
    private final UserUtility userUtility;
    private final QRCodeUtil qrCodeUtil;

    @Override
    public ResponseDto generateQRCode(Principal principal, Long subscriptionId) {

        if (principal == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_PRINCIPAL_IS_NULL);
        }

        User user = userUtility.findUserByPrincipal(principal);
        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasClientRole(user)){
            return ResponseUtility.getErrorResponse("E0003", MessageType.ERROR_NO_PERMISSION);
        }

        if (subscriptionId == null || subscriptionId == 0){
            return ResponseUtility.getErrorResponse("E0004", MessageType.ERROR_INVALID_REQUEST);
        }

        UserGymSubscription gymSubscription = userGymSubscriptionRepository.findById(subscriptionId).orElse(null);

        if (gymSubscription == null){
            return ResponseUtility.getErrorResponse("E0005", MessageType.ERROR_GYM_SUBSCRIPTION_NOT_EXIST);
        }

        UserGymSubscriptionQr userGymSubscriptionQr = userGymSubscriptionQrRepository.findByUserGymSubscriptionId(gymSubscription.getId()).orElse(null);

        if (userGymSubscriptionQr == null){
            return ResponseUtility.getErrorResponse("E0006", MessageType.ERROR_USER_GYM_SUBSCRIPTION_QR_NOT_FOUND);
        }

        if (!userGymSubscriptionQr.getIsQRTokenActive()){
            return ResponseUtility.getErrorResponse("E0007", MessageType.ERROR_USER_GYM_SUBSCRIPTION_QR_NOT_ACTIVE);
        }

        return qrCodeUtil.generateQRCode(userGymSubscriptionQr.getUserGymSubscriptionQRToken());
    }

    @Override
    public ResponseDto scanQRCode(Principal principal, MultipartFile qr) {
        if (principal == null){
            return ResponseUtility.getErrorResponse("E0001", MessageType.ERROR_PRINCIPAL_IS_NULL);
        }

        User user = userUtility.findUserByPrincipal(principal);
        if (user == null){
            return ResponseUtility.getErrorResponse("E0002", MessageType.ERROR_USER_NOT_EXIST);
        }

        if (!userUtility.hasClientRole(user)){
            return ResponseUtility.getErrorResponse("E0003", MessageType.ERROR_NO_PERMISSION);
        }

        if (qr.isEmpty()){
            return ResponseUtility.getErrorResponse("E0004", MessageType.ERROR_NO_INPUT_FILE_DETECTED);
        }

        try(InputStream inputStream = qr.getInputStream()){
            BufferedImage img = ImageIO.read(inputStream);

            if (img == null){
                return ResponseUtility.getErrorResponse("E0005", MessageType.ERROR_NO_INPUT_FILE_DETECTED);
            }

            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));

            String qrToken = new QRCodeReader().decode(bitmap).getText();

             UserGymSubscriptionQr subscription = userGymSubscriptionQrRepository.findByUserGymSubscriptionQRToken(qrToken).orElse(null);

             if (subscription == null){
                 return ResponseUtility.getErrorResponse("E0006", MessageType.ERROR_USER_GYM_SUBSCRIPTION_QR_NOT_FOUND);
             }

             if (!subscription.getIsQRTokenActive()){
                 return ResponseUtility.getErrorResponse("E0007", MessageType.ERROR_USER_GYM_SUBSCRIPTION_QR_NOT_ACTIVE);
             }

             return userActivityUtil.logSubscribedMemberActivity(user, subscription);

        }catch (Exception e){
            return ResponseUtility.getErrorResponse("E9999", MessageType.ERROR_SOMETHING_WENT_WRONG);
        }
    }
}
