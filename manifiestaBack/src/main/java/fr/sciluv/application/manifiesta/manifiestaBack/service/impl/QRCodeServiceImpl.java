package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.QRCodeRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.QRCodeService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service

public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    QRCodeRepository qrCodeRepository;

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
    public QRCode generateQRCode(Session session, UserLoginDto userLoginDto) {
        String url = generateUniqueId(9);
        User user = userService.getUser(userLoginDto.getUsername());
        System.out.println(session.getIdSession());
        System.out.println(url);
        return qrCodeRepository.save(new QRCode(url, session, user));
    }
}
