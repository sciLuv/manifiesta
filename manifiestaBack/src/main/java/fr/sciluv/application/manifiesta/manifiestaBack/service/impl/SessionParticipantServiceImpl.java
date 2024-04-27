package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SessionParticipantRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.QRCodeService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionParticipantService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionParticipantServiceImpl implements SessionParticipantService {

    @Autowired
    SessionParticipantRepository sessionParticipantRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    QRCodeService qrCodeService;

    @Autowired
    SessionService sessionService;

    @Override
    public SessionParticipant createParticipantForSessionOwner(User user, Session session) {
        SessionParticipant sessionParticipant = new SessionParticipant(
                user,
                session,
                "User"
        );
        return sessionParticipantRepository.save(sessionParticipant);
    }

    @Override
    public SessionParticipant createParticipantForSession(String username, String qrCode, String role) {

        QRCode realQRCode = qrCodeService.findQRCodeByInfo(qrCode);
        Session session = sessionService.findSessionByQrCode(realQRCode);
        boolean isUserAlreadyParticipant = isUserAlreadyParticipant(username, session);

        if(isUserAlreadyParticipant) return null;

        User user = userService.getUser(username);
        SessionParticipant sessionParticipant = new SessionParticipant(
                user,
                session,
                role
        );
        return sessionParticipantRepository.save(sessionParticipant);
    }

    @Override
    public boolean isUserAlreadyParticipant(String username, Session session) {

        User user = userService.getUser(username);
        SessionParticipant sessionParticipant = sessionParticipantRepository.findByUserAndSession(user, session);
        if(sessionParticipant != null)
            return true;
        else
            return false;
    }
}
