package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserLoginDto;
import org.springframework.stereotype.Service;


public interface QRCodeService {
    QRCode generateQRCode(Session session, UserLoginDto userLoginDto);
}
