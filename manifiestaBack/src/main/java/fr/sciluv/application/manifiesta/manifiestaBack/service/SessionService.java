package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.SessionParticipantDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationForHomePageDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationToSendDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;

import java.util.List;

public interface SessionService {
    Session createSession(SessionDto sessionDto, UserLoginDto userLoginDto);

    SessionInformationToSendDto joinSession(JoinSessionDto joinSessionDto, SessionParticipantDto sessionParticipant, String username);

    Session addQrCodeToSession(Session session, QRCode qrCode);

    Session findSessionByQrCode(QRCode qrCode);

    SessionInformationForHomePageDto findSessionInformationByQrCode(String qrCode);
    void createParticipantForSessionOwner(User user, Session session);
    SessionParticipantDto createParticipantForSession(String username, String qrCode, String role);

    SessionInformationForHomePageDto findOwnAndNotEndSessionInformation(String username);

    List<SessionInformationForHomePageDto> findParticipantNotEndSessionInformation(String username);

    boolean isSessionLinkedToUser(User user, Session session);

    void endSession(String username, String qrCodeInfo);

}
