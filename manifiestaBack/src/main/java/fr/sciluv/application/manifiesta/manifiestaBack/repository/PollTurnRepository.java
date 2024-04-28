package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PollTurnRepository extends JpaRepository<PollTurn, Long>{
    PollTurn findBySession(Session session);

    Set<PollTurn> findAllBySession(Session session);

    int countBySession(Session session);

    PollTurn findFirstBySessionOrderByNumberTurnDesc(Session session);
}
