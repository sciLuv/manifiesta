package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;

import java.util.Optional;


public interface QRCodeService {
    QRCode generateQRCode(UserLoginDto userLoginDto);

    QRCode generateGlobalQRCode(User user);

    QRCode findQRCodeByInfo(String qrCodeInfo);

    QRCode findQRCodeBySession(Session session);

    QRCode findQRCodeByUserAndIsGlobal(User user, boolean isGlobal);
}
