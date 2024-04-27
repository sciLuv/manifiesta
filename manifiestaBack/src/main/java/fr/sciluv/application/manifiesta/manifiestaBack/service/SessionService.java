package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationForHomePageDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationToSendDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;

public interface SessionService {
    Session createSession(SessionDto sessionDto, UserLoginDto userLoginDto);

    SessionInformationToSendDto joinSession(JoinSessionDto joinSessionDto);

    Session addQrCodeToSession(Session session, QRCode qrCode);

    Session findSessionByQrCode(QRCode qrCode);

//    SessionInformationForHomePageDto findOwnAndNotEndSessionInformation(String username);
}
