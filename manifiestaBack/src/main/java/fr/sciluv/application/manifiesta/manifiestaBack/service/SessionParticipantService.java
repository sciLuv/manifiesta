package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.SessionParticipantDto;

import java.util.Set;

public interface SessionParticipantService {
    SessionParticipant createParticipantForSessionOwner(User user, Session session);
    SessionParticipantDto createParticipantForSession(String username, String qrCode, String role);

    SessionParticipant isUserAlreadyParticipant(String username, Session session);

    int numberOfParticipantsInSession(Session session);

    Set<SessionParticipant> findAllSessionParticipantByUser(User user);

    Set<SessionParticipant> findAllSessionParticipantBySession(Session session);

    SessionParticipant saveSessionParticipant(SessionParticipant sessionParticipant);
}
