package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.QRCodeRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.QRCodeService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service

public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    QRCodeRepository qrCodeRepository;

    @Autowired
    SessionService sessionService;

    @Autowired
    UserService userService;
    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateUniqueId(int numBytes) {
        byte[] randomBytes = new byte[numBytes];
        random.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @Override
    public QRCode generateQRCode(UserLoginDto userLoginDto) {
        String url = generateUniqueId(9);
        User user = userService.getUser(userLoginDto.getUsername());

        QRCode qrCode = qrCodeRepository.save(new QRCode(url, user));
        return qrCode;
    }

    @Override
    public QRCode findQRCodeByInfo(String qrCodeInfo) {
        return qrCodeRepository.findByQrCodeInfo(qrCodeInfo).orElse(null);
    }
}
