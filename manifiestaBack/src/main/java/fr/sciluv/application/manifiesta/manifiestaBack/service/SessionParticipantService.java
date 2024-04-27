package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;

public interface SessionParticipantService {
    SessionParticipant createParticipantForSessionOwner(User user, Session session);
    SessionParticipant createParticipantForSession(String username, String qrCode, String role);

    boolean isUserAlreadyParticipant(String username, Session session);
}
