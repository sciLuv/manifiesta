package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Long> {
    SessionParticipant findByUserAndSession(User user, Session sesion);
}
