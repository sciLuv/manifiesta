package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.QRCode;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.SessionParticipantDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SessionParticipantRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.QRCodeService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionParticipantService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SessionParticipantServiceImpl implements SessionParticipantService {

    @Autowired
    SessionParticipantRepository sessionParticipantRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    QRCodeService qrCodeService;

    private final SessionService sessionService;

    @Autowired
    public SessionParticipantServiceImpl(@Lazy SessionService sessionService) {
        this.sessionService = sessionService;
    }

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
    public SessionParticipantDto createParticipantForSession(String username, String qrCode, String role) {
        System.out.println("entrer dans createParticipantForSession de SessionParticipantServiceImpl");
        QRCode realQRCode = qrCodeService.findQRCodeByInfo(qrCode);
        Session session = sessionService.findSessionByQrCode(realQRCode);
        SessionParticipant isUserAlreadyParticipant = isUserAlreadyParticipant(username, session);
        if(isUserAlreadyParticipant != null){
            if(isUserAlreadyParticipant.getHourOfLeave() != null){
                isUserAlreadyParticipant.setHourOfLeave(null);
                sessionParticipantRepository.save(isUserAlreadyParticipant);
            }
        }
        if(isUserAlreadyParticipant == null){
            User user = userService.getUser(username);
            SessionParticipant sessionParticipant = new SessionParticipant(
                    user,
                    session,
                    role
            );
            sessionParticipantRepository.save(sessionParticipant);
        }
        return new SessionParticipantDto(username);
    }

    @Override
    public SessionParticipant isUserAlreadyParticipant(String username, Session session) {

        User user = userService.getUser(username);
        SessionParticipant sessionParticipant = sessionParticipantRepository.findByUserAndSession(user, session);
        return sessionParticipant;
    }

    @Override
    public int numberOfParticipantsInSession(Session session) {
        return sessionParticipantRepository.findAllBySessionAndHourOfLeaveIsNull(session).size();
    }

    @Override
    public Set<SessionParticipant> findAllSessionParticipantByUser(User user) {
        return sessionParticipantRepository.findAllByUser(user);
    }

    @Override
    public Set<SessionParticipant> findAllSessionParticipantBySession(Session session) {
        return sessionParticipantRepository.findAllBySessionAndHourOfLeaveIsNull(session);
    }

    @Override
    public SessionParticipant saveSessionParticipant(SessionParticipant sessionParticipant) {
        return sessionParticipantRepository.save(sessionParticipant);
    }
}
