package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;

import java.util.Set;

public interface SessionParticipantService {
    SessionParticipant createParticipantForSessionOwner(User user, Session session);
    SessionParticipant createParticipantForSession(String username, String qrCode, String role);

    boolean isUserAlreadyParticipant(String username, Session session);

    int numberOfParticipantsInSession(Session session);

    Set<SessionParticipant> findAllSessionParticipantByUser(User user);
}
