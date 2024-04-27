package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollTurnRepository extends JpaRepository<PollTurn, Long>{
    PollTurn findBySession(Session session);

    int countBySession(Session session);
}
